/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.astro.internal.calc;

import java.util.Calendar;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateUtils;
import org.openhab.binding.astro.internal.model.Position;
import org.openhab.binding.astro.internal.model.Radiation;
import org.openhab.binding.astro.internal.model.Range;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunDailyEvents;
import org.openhab.binding.astro.internal.model.SunEclipse;
import org.openhab.binding.astro.internal.model.SunPhaseName;
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/**
 * Calculates the SunPosition (azimuth, elevation) and Sun data.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 * @see based on the calculations of http://www.suncalc.net
 */
public class SunCalc extends AbstractSunCalc {
    private static final double J2000 = 2451545.0;
    private static final double SC = 1367; // Solar constant in W/m²
    public static final double DEG2RAD = Math.PI / 180;
    public static final double RAD2DEG = 180. / Math.PI;

    private static final double MINUTES_PER_DAY = 60 * 24;
    private static final int CURVE_TIME_INTERVAL = 20; // 20 minutes
    private static final double JD_ONE_MINUTE_FRACTION = 1.0 / 60 / 24;

    /**
     * Calculates the sun position (azimuth and elevation).
     */
    public void setPositionalInfo(Calendar calendar, double latitude, double longitude, Double altitude, Sun sun) {
        double lw = -longitude * DEG2RAD;
        double phi = latitude * DEG2RAD;

        double j = DateTimeUtils.dateToJulianDate(calendar);
        double m = getSolarMeanAnomaly(j);
        double c = getEquationOfCenter(m);
        double lsun = getEclipticLongitude(m, c);
        double d = getSunDeclination(lsun);
        double a = getRightAscension(lsun);
        double th = getSiderealTime(j, lw);

        double azimuth = getAzimuth(th, a, phi, d) / DEG2RAD;
        double elevation = getElevation(th, a, phi, d) / DEG2RAD;
        double shadeLength = getShadeLength(elevation);

        Position position = sun.getPosition();
        position.setAzimuth(azimuth + 180);
        position.setElevation(elevation);
        position.setShadeLength(shadeLength);

        setRadiationInfo(calendar, elevation, altitude, sun);
    }

    /**
     * Calculates sun radiation data.
     */
    public void setRadiationInfo(Calendar calendar, double elevation, Double altitude, Sun sun) {
        double sinAlpha = Math.sin(DEG2RAD * elevation);

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        // Direct Solar Radiation (in W/m²) at the atmosphere entry
        // At sunrise/sunset - calculations limits are reached
        double rOut = (elevation > 3) ? SC * (0.034 * Math.cos(DEG2RAD * (360 * dayOfYear / daysInYear)) + 1) : 0;
        double altitudeRatio = (altitude != null) ? 1 / Math.pow((1 - (6.5 / 288) * (altitude / 1000.0)), 5.256) : 1;
        double m = (Math.sqrt(1229 + Math.pow(614 * sinAlpha, 2)) - 614 * sinAlpha) * altitudeRatio;

        // Direct radiation after atmospheric layer
        // 0.6 = Coefficient de transmissivité
        double rDir = rOut * Math.pow(0.6, m) * sinAlpha;

        // Diffuse Radiation
        double rDiff = rOut * (0.271 - 0.294 * Math.pow(0.6, m)) * sinAlpha;
        double rTot = rDir + rDiff;

        Radiation radiation = sun.getRadiation();
        radiation.setDirect(rDir);
        radiation.setDiffuse(rDiff);
        radiation.setTotal(rTot);
    }

    /**
     * Calculates all sun rise and sets at the specified coordinates.
     */
    public Sun getSunInfo(Calendar calendar, double latitude, double longitude, Double altitude) {
        Sun sun = new Sun();

        setDailyEvents(calendar, latitude, longitude, altitude, sun, false);
        setEclipse(calendar, sun);
        setZodiac(calendar, sun);
        setSeason(calendar, latitude, sun);
        setPhase(sun);

        return sun;
    }

    private void setDailyEvents(Calendar calendar, double latitude, double longitude, Double altitude, Sun sun,
            boolean onlyAstro) {
        SunDailyEventsCalc sunDailyEventsCalc = new SunDailyEventsCalc();
        SunDailyEvents sunDailyEvents = sunDailyEventsCalc.calculate(calendar, latitude, longitude, altitude);

        sun.setMorningNight(sunDailyEvents.getMorningNightRange());
        sun.setAstroDawn(sunDailyEvents.getAstroDawnRange());
        sun.setNauticDawn(sunDailyEvents.getNauticDawnRange());
        sun.setCivilDawn(sunDailyEvents.getCivilDawnRange());
        sun.setRise(sunDailyEvents.getRiseRange());
        sun.setDaylight(sunDailyEvents.getDaylightRange());
        sun.setNoon(sunDailyEvents.getNoonRange());
        sun.setSet(sunDailyEvents.getSetRange());
        sun.setCivilDusk(sunDailyEvents.getCivilDuskRange());
        sun.setNauticDusk(sunDailyEvents.getNauticDuskRange());
        sun.setAstroDusk(sunDailyEvents.getAstroDuskRange());
        sun.setEveningNight(sunDailyEvents.getEveningNightRange());

        sun.setTrueMidnight(sunDailyEvents.trueMidnight);
        sun.setNextTrueMidnight(sunDailyEvents.nextTrueMidnight);

        // if we are between midnight and true midnight then a ranges from previous day
        // need to be taken into account
        Calendar midnight = DateTimeUtils.truncateToMidnight(calendar);
        Range betweenMidnightAndTrueMidnight = new Range(midnight, sunDailyEvents.trueMidnight);
        if (betweenMidnightAndTrueMidnight.matches(calendar)) {
            Calendar yesterday = addDays(calendar, -1);
            SunDailyEvents yesterdayDailyEvents = sunDailyEventsCalc.calculate(yesterday, latitude, longitude,
                    altitude);
            if (yesterdayDailyEvents.getEveningNightRange().hasIntersection(betweenMidnightAndTrueMidnight)) {
                sun.setEveningNight(yesterdayDailyEvents.getEveningNightRange());
            }
            if (yesterdayDailyEvents.getAstroDuskRange().hasIntersection(betweenMidnightAndTrueMidnight)) {
                sun.setAstroDusk(yesterdayDailyEvents.getAstroDuskRange());
            }

            sun.setNight(new Range(yesterdayDailyEvents.getEveningNightRange().getStart(),
                    sunDailyEvents.getMorningNightRange().getEnd()));
        } else {
            if (sunDailyEvents.getMorningNightRange().matches(calendar)) {

                // the morning night end is calculated correctly, however it would be good to
                // have a night start as well. For now lets calculate this date as a symmetric
                // event to the morning night.
                long deltaMillis = sunDailyEvents.getMorningNightRange().getEnd().getTimeInMillis()
                        - sunDailyEvents.trueMidnight.getTimeInMillis();
                Calendar nightStart = (Calendar) sunDailyEvents.trueMidnight.clone();
                nightStart.setTimeInMillis(sunDailyEvents.trueMidnight.getTimeInMillis() - deltaMillis);

                sun.setNight(new Range(nightStart, sunDailyEvents.getMorningNightRange().getEnd()));
            } else if (sunDailyEvents.getEveningNightRange().isBounded()) {
                // the evening night start is calculated correctly, however it would be good to
                // have a night end as well. For now lets calculate this date as a symmetric
                // event to the evening night.
                long deltaMillis = sunDailyEvents.nextTrueMidnight.getTimeInMillis()
                        - sunDailyEvents.getEveningNightRange().getStart().getTimeInMillis();
                Calendar nightEnd = (Calendar) sunDailyEvents.nextTrueMidnight.clone();
                nightEnd.setTimeInMillis(sunDailyEvents.nextTrueMidnight.getTimeInMillis() + deltaMillis);

                sun.setNight(new Range(sunDailyEvents.getEveningNightRange().getStart(), nightEnd));
            } else {
                sun.setNight(new Range());
            }
        }

        checkForTotalDaylight(sunDailyEvents, latitude, longitude, sun);
    }

    private void checkForTotalDaylight(SunDailyEvents sunDailyEvents, double latitude, double longitude, Sun sun) {
        if (sunDailyEvents.getRiseRange().isBounded()) {
            // no total daylight
            return;
        }

        if (sunDailyEvents.getSetRange().isBounded()) {
            // no total daylight
            return;
        }

        // at this moment there is no rise nor set
        Sun positionalInfo = new Sun();
        setPositionalInfo(sunDailyEvents.transit, latitude, longitude, 0.0, positionalInfo);

        if (positionalInfo.getPosition().getElevationAsDouble() < 0) {
            // there is no total daylight
            return;
        }

        // there is a total daylight
        // need to find a start time and end time of total daylight
        Calendar cal = (Calendar) sunDailyEvents.transit.clone();
        SunDailyEventsCalc sunDailyEventsCalc = new SunDailyEventsCalc();
        Calendar daylightStart = null;
        Calendar daylightEnd = null;
        for (int q = 0; q < 190; q++) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            SunDailyEvents sde = sunDailyEventsCalc.calculate(cal, latitude, longitude, 0.0);
            if (sde.riseEnd != null) {
                // we have daylight start time
                daylightStart = sde.riseEnd;
                break;
            }
        }
        for (int q = 0; q < 190; q++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            SunDailyEvents sde = sunDailyEventsCalc.calculate(cal, latitude, longitude, 0.0);
            if (sde.setStart != null) {
                // we have daylight end time
                daylightEnd = sde.setStart;
                break;
            }
        }
        sun.setDaylight(new Range(daylightStart, daylightEnd));
    }

    private void setEclipse(Calendar calendar, Sun sun) {
        SunEclipse eclipse = sun.getEclipse();
        MoonCalc mc = new MoonCalc();
        double j = DateTimeUtils.midnightDateToJulianDate(calendar) + 0.5;

        double partial = mc.getEclipse(calendar, MoonCalc.ECLIPSE_TYPE_SUN, j, MoonCalc.ECLIPSE_MODE_PARTIAL);
        eclipse.setPartial(DateTimeUtils.toCalendar(partial));
        double ring = mc.getEclipse(calendar, MoonCalc.ECLIPSE_TYPE_SUN, j, MoonCalc.ECLIPSE_MODE_RING);
        eclipse.setRing(DateTimeUtils.toCalendar(ring));
        double total = mc.getEclipse(calendar, MoonCalc.ECLIPSE_TYPE_SUN, j, MoonCalc.ECLIPSE_MODE_TOTAL);
        eclipse.setTotal(DateTimeUtils.toCalendar(total));
    }

    private void setZodiac(Calendar calendar, Sun sun) {
        SunZodiacCalc zodiacCalc = new SunZodiacCalc();
        sun.setZodiac(zodiacCalc.getZodiac(calendar));
    }

    private void setSeason(Calendar calendar, double latitude, Sun sun) {
        SeasonCalc seasonCalc = new SeasonCalc();
        sun.setSeason(seasonCalc.getSeason(calendar, latitude));
    }

    private void setPhase(Sun sun) {
        // phase
        for (Entry<SunPhaseName, Range> rangeEntry : sun.getAllRanges().entrySet()) {
            SunPhaseName entryPhase = rangeEntry.getKey();
            if (rangeEntry.getValue().matches(Calendar.getInstance())) {
                if (entryPhase == SunPhaseName.MORNING_NIGHT || entryPhase == SunPhaseName.EVENING_NIGHT) {
                    sun.getPhase().setName(SunPhaseName.NIGHT);
                } else {
                    sun.getPhase().setName(entryPhase);
                }
            }
        }
    }

    /**
     * Returns true, if the sun is up all day (no rise and set).
     */
    private boolean isSunUpAllDay(Calendar calendar, double latitude, double longitude, Double altitude) {
        Calendar cal = DateTimeUtils.truncateToMidnight(calendar);
        Sun sun = new Sun();
        for (int minutes = 0; minutes <= MINUTES_PER_DAY; minutes += CURVE_TIME_INTERVAL) {
            setPositionalInfo(cal, latitude, longitude, altitude, sun);
            if (sun.getPosition().getElevationAsDouble() < SUN_ANGLE) {
                return false;
            }
            cal.add(Calendar.MINUTE, CURVE_TIME_INTERVAL);
        }
        return true;
    }

    /**
     * Adds the specified days to the calendar.
     */
    private Calendar addDays(Calendar calendar, int days) {
        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal;
    }
}
