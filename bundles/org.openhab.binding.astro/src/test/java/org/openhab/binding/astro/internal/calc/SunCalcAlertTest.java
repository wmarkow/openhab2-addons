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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.astro.TestUtils;
import org.openhab.binding.astro.internal.model.Sun;

/***
 * Tests the sun daily ranges for Alert location in Canada. In particular it
 * checks:
 * <ul>
 * <li>astronomical twilight and night at 21 December</li>
 * <li>a full daylight at 21 June</li>
 * </ul>
 * 
 * @author Witold Markowski
 * @see <a href="https://www.timeanddate.com/sun/canada/alert">Sunrise and
 *      sunset times in Alert</a>
 */
public class SunCalcAlertTest extends AbstractSunCalcLocationTest {

    private final static double LATITUDE = 82.5059388;
    private final static double LONGITUDE = -62.5296812;
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("EST5EDT");

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_Dec_21_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 0, 0, TIME_ZONE);

        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        assertNull(sun.getDaylight().getStart());
        assertNull(sun.getDaylight().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 8, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getSet().getStart());
        assertNull(sun.getSet().getEnd());

        assertNull(sun.getCivilDusk().getStart());
        assertNull(sun.getCivilDusk().getEnd());

        assertNull(sun.getNauticDusk().getStart());
        assertNull(sun.getNauticDusk().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 23, 7, TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show current night from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 14, 4, TIME_ZONE).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // TRUE MIDNIGHT is on 20th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Dec_21_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_Dec_21_in_astroDusk() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 12, 0, TIME_ZONE);
        TimeZone.getAvailableIDs();
        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        assertNull(sun.getDaylight().getStart());
        assertNull(sun.getDaylight().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 8, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getSet().getStart());
        assertNull(sun.getSet().getEnd());

        assertNull(sun.getCivilDusk().getStart());
        assertNull(sun.getCivilDusk().getEnd());

        assertNull(sun.getNauticDusk().getStart());
        assertNull(sun.getNauticDusk().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 23, 7, TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show current night from 21th/22th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, TIME_ZONE).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 22, 8, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // TRUE MIDNIGHT is on 20th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Dec_21_in_astroDusk() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 12, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_Jun_21_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 0, TIME_ZONE);

        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        // according to https://www.timeanddate.com/sun/canada/alert
        // the DAYLIGHT starts on 2019 APR 6 01:18 local time but this is a moment of
        // SUN RISE start
        // In Astro DAYLIGHT starts when SUN RISE ends.
        assertEquals(TestUtils.newCalendar(2019, Calendar.APRIL, 8, 0, 39, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // according to https://www.timeanddate.com/sun/canada/alert
        // the DAYLIGHT ends on 2019 SEPT 5 23:33 local time but this is a moment of
        // SUN SET end
        // In Astro DAYLIGHT ends when SUN SET starts
        assertEquals(TestUtils.newCalendar(2019, Calendar.SEPTEMBER, 5, 23, 3, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 12, 11, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 12, 12, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getSet().getStart());
        assertNull(sun.getSet().getEnd());

        assertNull(sun.getCivilDusk().getStart());
        assertNull(sun.getCivilDusk().getEnd());

        assertNull(sun.getNauticDusk().getStart());
        assertNull(sun.getNauticDusk().getEnd());

        assertNull(sun.getAstroDusk().getStart());
        assertNull(sun.getAstroDusk().getEnd());

        assertNull(sun.getMorningNight().getStart());
        assertNull(sun.getMorningNight().getEnd());

        assertNull(sun.getEveningNight().getStart());
        assertNull(sun.getEveningNight().getEnd());

        assertNull(sun.getNight().getStart());
        assertNull(sun.getNight().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 10, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Jun_21_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }
}
