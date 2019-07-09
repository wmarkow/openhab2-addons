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

import org.openhab.binding.astro.internal.model.Position;
import org.openhab.binding.astro.internal.model.Range;
import org.openhab.binding.astro.internal.model.SunDailyEvents;
import org.openhab.binding.astro.internal.model.SunDailyRanges;
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/***
 * For a specific date, time and geolocation calculates {@link SunDailyRanges}.
 * 
 * @author Witold Markowski
 *
 */
public class SunDailyRangesCalc {

    public SunDailyRanges calculate(Calendar calendar, double latitude, double longitude, Double altitude) {
        SunDailyEventsCalc sunDailyEventsCalc = new SunDailyEventsCalc();
        SunDailyEvents sunDailyEvents = sunDailyEventsCalc.calculate(calendar, latitude, longitude, altitude);

        SunDailyRanges result = new SunDailyRanges();

        result.setMorningNight(sunDailyEvents.getMorningNightRange());
        result.setAstroDawn(sunDailyEvents.getAstroDawnRange());
        result.setNauticDawn(sunDailyEvents.getNauticDawnRange());
        result.setCivilDawn(sunDailyEvents.getCivilDawnRange());
        result.setRise(sunDailyEvents.getRiseRange());
        result.setDaylight(sunDailyEvents.getDaylightRange());
        result.setNoon(sunDailyEvents.getNoonRange());
        result.setSet(sunDailyEvents.getSetRange());
        result.setCivilDusk(sunDailyEvents.getCivilDuskRange());
        result.setNauticDusk(sunDailyEvents.getNauticDuskRange());
        result.setAstroDusk(sunDailyEvents.getAstroDuskRange());
        result.setEveningNight(sunDailyEvents.getEveningNightRange());

        result.setTrueMidnight(sunDailyEvents.trueMidnight);
        result.setNextTrueMidnight(sunDailyEvents.nextTrueMidnight);

        // if we are between midnight and true midnight then a ranges from previous day
        // need to be taken into account
        Calendar midnight = DateTimeUtils.truncateToMidnight(calendar);
        Range betweenMidnightAndTrueMidnight = new Range(midnight, sunDailyEvents.trueMidnight);
        if (betweenMidnightAndTrueMidnight.matches(calendar)) {
            Calendar yesterday = DateTimeUtils.addDays(calendar, -1);
            SunDailyEvents yesterdayDailyEvents = sunDailyEventsCalc.calculate(yesterday, latitude, longitude,
                    altitude);
            if (yesterdayDailyEvents.getEveningNightRange().hasIntersection(betweenMidnightAndTrueMidnight)) {
                result.setEveningNight(yesterdayDailyEvents.getEveningNightRange());
            }
            if (yesterdayDailyEvents.getAstroDuskRange().hasIntersection(betweenMidnightAndTrueMidnight)) {
                result.setAstroDusk(yesterdayDailyEvents.getAstroDuskRange());
            }
            if (yesterdayDailyEvents.getNauticDuskRange().hasIntersection(betweenMidnightAndTrueMidnight)) {
                result.setNauticDusk(yesterdayDailyEvents.getNauticDuskRange());
            }
            if (yesterdayDailyEvents.getCivilDuskRange().hasIntersection(betweenMidnightAndTrueMidnight)) {
                result.setCivilDusk(yesterdayDailyEvents.getCivilDuskRange());
            }

            result.setNight(new Range(yesterdayDailyEvents.getEveningNightRange().getStart(),
                    sunDailyEvents.getMorningNightRange().getEnd()));
        } else {
            if (sunDailyEvents.getMorningNightRange().matches(calendar)) {

                // the morning night end is calculated correctly, however it would be good to
                // have a night start as well. For now lets calculate this date as a symmetric
                // event to the morning night.
                // TODO: is using a symetric event good enough here? Would it be better to
                // calculate a sun daily events from yesterday?
                long deltaMillis = sunDailyEvents.getMorningNightRange().getEnd().getTimeInMillis()
                        - sunDailyEvents.trueMidnight.getTimeInMillis();
                Calendar nightStart = (Calendar) sunDailyEvents.trueMidnight.clone();
                nightStart.setTimeInMillis(sunDailyEvents.trueMidnight.getTimeInMillis() - deltaMillis);

                result.setNight(new Range(nightStart, sunDailyEvents.getMorningNightRange().getEnd()));
            } else if (sunDailyEvents.getEveningNightRange().isBounded()) {
                // the evening night start is calculated correctly, however it would be good to
                // have a night end as well. For now lets calculate this date as a symmetric
                // event to the evening night.
                // TODO: is using a symetric event good enough here? Would it be better to
                // calculate a sun daily events from tomorrow?
                long deltaMillis = sunDailyEvents.nextTrueMidnight.getTimeInMillis()
                        - sunDailyEvents.getEveningNightRange().getStart().getTimeInMillis();
                Calendar nightEnd = (Calendar) sunDailyEvents.nextTrueMidnight.clone();
                nightEnd.setTimeInMillis(sunDailyEvents.nextTrueMidnight.getTimeInMillis() + deltaMillis);

                result.setNight(new Range(sunDailyEvents.getEveningNightRange().getStart(), nightEnd));
            } else {
                result.setNight(new Range());
            }
        }

        checkForTotalDaylight(sunDailyEvents, latitude, longitude, result);

        return result;
    }

    private void checkForTotalDaylight(SunDailyEvents sunDailyEvents, double latitude, double longitude,
            SunDailyRanges sunDailyRanges) {
        if (sunDailyEvents.getRiseRange().isBounded()) {
            // no total daylight
            return;
        }

        if (sunDailyEvents.getSetRange().isBounded()) {
            // no total daylight
            return;
        }

        // at this moment there is no rise nor set
        SunPositionCalc sunPositionCalc = new SunPositionCalc();
        Position position = sunPositionCalc.calculate(sunDailyEvents.transit, latitude, longitude);

        if (position.getElevationAsDouble() < 0) {
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
        sunDailyRanges.setDaylight(new Range(daylightStart, daylightEnd));
    }
}
