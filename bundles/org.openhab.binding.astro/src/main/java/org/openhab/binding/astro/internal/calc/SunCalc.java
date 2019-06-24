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
     * Calculates all sun rise and sets at the specified coordinates.
     */
    public Sun getSunInfo(Calendar calendar, double latitude, double longitude, Double altitude) {
        return getSunInfo(calendar, latitude, longitude, altitude, false);
    }

    private Sun getSunInfo(Calendar calendar, double latitude, double longitude, Double altitude, boolean onlyAstro) {
        SunDailyEventsCalc sunDailyEventsCalc = new SunDailyEventsCalc();
        SunDailyEvents sunDailyEvents = sunDailyEventsCalc.calculate(calendar, latitude, longitude, altitude);

        Calendar astroDawnStart = sunDailyEvents.astroDawnStart;
        Calendar nauticDawnStart = sunDailyEvents.nauticDawnStart;
        Calendar astroDuskStart = sunDailyEvents.astroDuskStart;
        Calendar nightStart = sunDailyEvents.nightStart;
        Calendar transit = sunDailyEvents.transit;
        Calendar riseStart = sunDailyEvents.riseStart;
        Calendar riseEnd = sunDailyEvents.riseEnd;
        Calendar setStart = sunDailyEvents.setStart;
        Calendar setEnd = sunDailyEvents.setEnd;
        Calendar civilDawnStart = sunDailyEvents.civilDawnStart;
        Calendar nauticDuskStart = sunDailyEvents.nauticDuskStart;

        Sun sun = new Sun();
        sun.setAstroDawn(new Range(astroDawnStart, nauticDawnStart));
        sun.setAstroDusk(new Range(astroDuskStart, nightStart));

        if (onlyAstro) {
            return sun;
        }
        
        final Calendar transitEnd = ((Calendar)transit.clone());
        transitEnd.add(Calendar.MINUTE, 1);
        sun.setNoon(new Range(transit, transitEnd));
        sun.setRise(new Range(riseStart, riseEnd));
        sun.setSet(new Range(setStart, setEnd));

        sun.setCivilDawn(new Range(civilDawnStart, riseStart));
        sun.setCivilDusk(new Range(setEnd, nauticDuskStart));

        sun.setNauticDawn(new Range(nauticDawnStart, civilDawnStart));
        sun.setNauticDusk(new Range(nauticDuskStart, astroDuskStart));

        boolean isSunUpAllDay = isSunUpAllDay(calendar, latitude, longitude, altitude);

        // daylight
        Range daylightRange = new Range();
        if (sun.getRise().getStart() == null && sun.getRise().getEnd() == null) {
            if (isSunUpAllDay) {
                daylightRange = new Range(DateTimeUtils.truncateToMidnight(calendar),
                        DateTimeUtils.truncateToMidnight(addDays(calendar, 1)));
            }
        } else {
            daylightRange = new Range(sun.getRise().getEnd(), sun.getSet().getStart());
        }
        sun.setDaylight(daylightRange);

        // morning night
        Sun sunYesterday = getSunInfo(addDays(calendar, -1), latitude, longitude, altitude, true);
        Range morningNightRange = null;
        if (sunYesterday.getAstroDusk().getEnd() != null
                && DateUtils.isSameDay(sunYesterday.getAstroDusk().getEnd(), calendar)) {
            morningNightRange = new Range(sunYesterday.getAstroDusk().getEnd(), sun.getAstroDawn().getStart());
        } else if (isSunUpAllDay || sun.getAstroDawn().getStart() == null) {
            morningNightRange = new Range();
        } else {
            morningNightRange = new Range(DateTimeUtils.truncateToMidnight(calendar), sun.getAstroDawn().getStart());
        }
        sun.setMorningNight(morningNightRange);

        // evening night
        Range eveningNightRange = null;
        if (sun.getAstroDusk().getEnd() != null && DateUtils.isSameDay(sun.getAstroDusk().getEnd(), calendar)) {
            eveningNightRange = new Range(sun.getAstroDusk().getEnd(),
                    DateTimeUtils.truncateToMidnight(addDays(calendar, 1)));
        } else {
            eveningNightRange = new Range();
        }
        sun.setEveningNight(eveningNightRange);

        // night
        if (isSunUpAllDay) {
            sun.setNight(new Range());
        } else {
            Sun sunTomorrow = getSunInfo(addDays(calendar, 1), latitude, longitude, altitude, true);
            sun.setNight(new Range(sun.getAstroDusk().getEnd(), sunTomorrow.getAstroDawn().getStart()));
        }

        // eclipse
        SunEclipse eclipse = sun.getEclipse();
        MoonCalc mc = new MoonCalc();
        double j = DateTimeUtils.midnightDateToJulianDate(calendar) + 0.5;

        double partial = mc.getEclipse(calendar, MoonCalc.ECLIPSE_TYPE_SUN, j, MoonCalc.ECLIPSE_MODE_PARTIAL);
        eclipse.setPartial(DateTimeUtils.toCalendar(partial));
        double ring = mc.getEclipse(calendar, MoonCalc.ECLIPSE_TYPE_SUN, j, MoonCalc.ECLIPSE_MODE_RING);
        eclipse.setRing(DateTimeUtils.toCalendar(ring));
        double total = mc.getEclipse(calendar, MoonCalc.ECLIPSE_TYPE_SUN, j, MoonCalc.ECLIPSE_MODE_TOTAL);
        eclipse.setTotal(DateTimeUtils.toCalendar(total));

        SunZodiacCalc zodiacCalc = new SunZodiacCalc();
        sun.setZodiac(zodiacCalc.getZodiac(calendar));

        SeasonCalc seasonCalc = new SeasonCalc();
        sun.setSeason(seasonCalc.getSeason(calendar, latitude));

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

        return sun;
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
