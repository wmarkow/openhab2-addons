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

import org.openhab.binding.astro.internal.model.Position;
import org.openhab.binding.astro.internal.model.Radiation;
import org.openhab.binding.astro.internal.model.Range;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunDailyRanges;
import org.openhab.binding.astro.internal.model.SunEclipse;
import org.openhab.binding.astro.internal.model.SunPhaseName;
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/**
 * Calculates the SunPosition (azimuth, elevation) and Sun data.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 * @author Witold Markowski - Rafactoring
 * @see based on the calculations of http://www.suncalc.net
 */
public class SunCalc extends AbstractSunCalc {
    public static final double DEG2RAD = Math.PI / 180;
    public static final double RAD2DEG = 180. / Math.PI;

    /**
     * Calculates the sun position (azimuth and elevation).
     */
    public void setPositionalInfo(Calendar calendar, double latitude, double longitude, Double altitude, Sun sun) {
        SunPositionCalc sunPositionCalc = new SunPositionCalc();
        Position position = sunPositionCalc.calculate(calendar, latitude, longitude);

        sun.setPosition(position);

        setRadiationInfo(calendar, position.getElevationAsDouble(), altitude, sun);
    }

    /**
     * Calculates sun radiation data.
     */
    private void setRadiationInfo(Calendar calendar, double elevation, Double altitude, Sun sun) {
        SunRadiationCalc sunRadiationCalc = new SunRadiationCalc();
        Radiation radiation = sunRadiationCalc.calculate(calendar, elevation, altitude);

        sun.getRadiation().setDirect(radiation.getDirect().doubleValue());
        sun.getRadiation().setDiffuse(radiation.getDiffuse().doubleValue());
        sun.getRadiation().setTotal(radiation.getTotal().doubleValue());
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
        SunDailyRangesCalc sunDailyRangesCalc = new SunDailyRangesCalc();
        SunDailyRanges sunDailyRanges = sunDailyRangesCalc.calculate(calendar, latitude, longitude, altitude);

        sun.setMorningNight(sunDailyRanges.getMorningNight());
        sun.setAstroDawn(sunDailyRanges.getAstroDawn());
        sun.setNauticDawn(sunDailyRanges.getNauticDawn());
        sun.setCivilDawn(sunDailyRanges.getCivilDawn());
        sun.setRise(sunDailyRanges.getRise());
        sun.setDaylight(sunDailyRanges.getDaylight());
        sun.setNoon(sunDailyRanges.getNoon());
        sun.setSet(sunDailyRanges.getSet());
        sun.setCivilDusk(sunDailyRanges.getCivilDusk());
        sun.setNauticDusk(sunDailyRanges.getNauticDusk());
        sun.setAstroDusk(sunDailyRanges.getAstroDusk());
        sun.setEveningNight(sunDailyRanges.getEveningNight());
        sun.setNight(sunDailyRanges.getNight());

        sun.setTrueMidnight(sunDailyRanges.getTrueMidnight());
        sun.setNextTrueMidnight(sunDailyRanges.getNextTrueMidnight());
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
}
