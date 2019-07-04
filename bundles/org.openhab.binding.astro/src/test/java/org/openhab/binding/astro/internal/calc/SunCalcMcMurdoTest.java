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
 * Tests the sun daily events for McMurdo location in Antarctica. In particular
 * it checks:
 * <ul>
 * <li>a full daylight at 21 December</li>
 * <li>a civil twilight and daylight at 27 February</li>
 * </ul>
 * 
 * @author Witold Markowski
 * @see <a href="https://www.timeanddate.com/sun/antarctica/mcmurdo">Sunrise and
 *      sunset times in McMurdo</a>
 */
public class SunCalcMcMurdoTest extends AbstractSunCalcLocationTest {

    private final static double LATITUDE = -77.8401191;
    private final static double LONGITUDE = 166.6445298;
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("NZ");

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_Dec_21_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 0, 0, TIME_ZONE);

        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        // according to https://www.timeanddate.com/sun/antarctica/mcmurdo
        // the DAYLIGHT starts on 2019 OCT 23 02:27 local time but this is a moment of
        // SUN RISE start
        // In Astro DAYLIGHT starts when SUN RISE ends.
        assertEquals(TestUtils.newCalendar(2019, Calendar.OCTOBER, 25, 1, 57, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // according to https://www.timeanddate.com/sun/antarctica/mcmurdo
        // the DAYLIGHT ends on 2020 FEB 20 01:43 local time but this is a moment of
        // SUN SET end
        // In Astro DAYLIGHT ends when SUN SET starts
        assertEquals(TestUtils.newCalendar(2020, Calendar.FEBRUARY, 20, 1, 26, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 13, 51, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 13, 52, TIME_ZONE).getTimeInMillis(),
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

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 1, 51, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Dec_21_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_Feb_27_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 0, 0, TIME_ZONE);

        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        // show civil dawn from 27th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 6, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 4, 42, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // sun rise takes about 20 minutes?
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 4, 42, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 0, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 0, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 21, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 14, 6, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 14, 7, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 16, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 21, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);

        // show civil dusk from 26th/27th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 26, 23, 32, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 6, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

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

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 6, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Feb_27_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_Feb_27_after_true_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 30, TIME_ZONE);

        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        // show civil dawn from 27th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 6, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 4, 42, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // sun rise takes about 20 minutes?
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 4, 42, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 0, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 0, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 21, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 14, 6, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 14, 7, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 16, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 21, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);

        // show civil dusk from 27th/28th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 23, 21, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 28, 2, 6, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

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

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 6, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Feb_27_after_true_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 2, 30, TIME_ZONE), LATITUDE, LONGITUDE);
    }
}
