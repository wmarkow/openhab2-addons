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
package org.openhab.binding.astro.internal.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openhab.binding.astro.internal.util.DateTimeUtils;

/***
 * A class which calculates the next date time instant, where sun daily events
 * should be recalculated. Sun daily events should be recalculated as a result
 * of daily sun phase changes, in particular:
 * <ul>
 * <li>shortly after local MIDNIGHT: the date changes so the daily events must
 * be recalculated</li>
 * <li>shortly after local TRUE MIDNIGHT: the sun reaches its lowest elevation
 * above/below horizon. It affects the NIGHT, MORNING_NIGHT and EVENING_NIGHT
 * events.</li>
 * <li>shortly after local MORNING_NIGHT ends: night has ended. It affects the
 * NIGHT, MORNING_NIGHT and EVENING_NIGHT events.</li>
 * </ul>
 * 
 * @author Witold Markowski - Initial contribution
 *
 */
public class DailyJobSunScheduler {

    /***
     * For a specific set of input dates calculates the next time when the sun daily
     * events should be recalculated.
     * 
     * @param now
     *            a time instant that represent current date time. It is also used
     *            to calculate MIDNIGHT instant.
     * @param noon
     *            a time instant of the NOON event. It is used to calculate TRUE
     *            MIDNIGHT instant.
     * @param morningNightEnd
     *            a time instant of MORNING_NIGHT_END event
     * @return The next time of daily events recalculation. Which is:
     *         <ul>
     *         <li>30 seconds after MIDNIGHT</li>
     *         <li>or 30 seconds after TRUE MIDNIGHT</li>
     *         <li>or 30 seconds after MORNING_NIGHT_END (if night is present)</li>
     *         </ul>
     *         whatever comes first after the current date time instant.
     */
    public Calendar findNextExecutionTime(Calendar now, Calendar noon, Calendar morningNightEnd) {
        if (now == null) {
            throw new IllegalArgumentException("Now can't be null.");
        }
        if (noon == null) {
            throw new IllegalArgumentException("Noon can't be null.");
        }

        List<Calendar> list = new ArrayList<Calendar>();

        addToListIfPossible(list, now, getNextMidnight(now));
        addToListIfPossible(list, now, getTrueMidnight(noon));
        addToListIfPossible(list, now, getNextTrueMidnight(noon));
        addToListIfPossible(list, now, morningNightEnd);

        Collections.sort(list, new CalendarAscendingComparator());

        Calendar result = (Calendar) list.get(0).clone();
        result.add(Calendar.SECOND, 30);

        return result;
    }

    private Calendar getNextMidnight(Calendar calendar) {
        Calendar result = DateTimeUtils.truncateToMidnight(calendar);
        result.add(Calendar.HOUR_OF_DAY, 24);

        return result;
    }

    private Calendar getTrueMidnight(Calendar noon) {
        Calendar result = (Calendar) noon.clone();
        result.add(Calendar.HOUR_OF_DAY, -12);

        return result;
    }

    private Calendar getNextTrueMidnight(Calendar noon) {
        Calendar result = (Calendar) noon.clone();
        result.add(Calendar.HOUR_OF_DAY, 12);

        return result;
    }

    private void addToListIfPossible(List<Calendar> list, Calendar now, Calendar toAdd) {
        if (toAdd == null) {
            return;
        }

        if (toAdd.getTimeInMillis() > now.getTimeInMillis()) {
            list.add(toAdd);
        }
    }

    private class CalendarAscendingComparator implements Comparator<Calendar> {

        @Override
        public int compare(Calendar o1, Calendar o2) {
            if (o1.getTimeInMillis() > o2.getTimeInMillis()) {
                return 1;
            }
            if (o1.getTimeInMillis() < o2.getTimeInMillis()) {
                return -1;
            }
            return 0;
        }
    }
}
