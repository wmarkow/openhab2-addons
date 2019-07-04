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
 * Tests the sun daily ranges for Malmo location in Sweden. In particular it
 * checks:
 * <ul>
 * <li>a full daylight at 21 June</li>
 * </ul>
 * 
 * @author Witold Markowski
 * @see <a href="https://www.timeanddate.com/sun/sweden/malmo">Sunrise and
 *      sunset times in Malmo</a>
 */
public class SunCalcMalmoTest extends AbstractSunCalcLocationTest {

    private final static double LATITUDE = 55.5700886;
    private final static double LONGITUDE = 12.8758905;
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Stockholm");

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_June_21_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 0, TIME_ZONE);
        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 3, 23, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 3, 23, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 24, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 24, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 29, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 29, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 50, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 13, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 13, 10, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 50, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 55, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 55, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 22, 56, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show nautic dusk from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 20, 22, 56, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getAstroDusk().getStart());
        assertNull(sun.getAstroDusk().getEnd());

        assertNull(sun.getMorningNight().getStart());
        assertNull(sun.getMorningNight().getEnd());

        assertNull(sun.getEveningNight().getStart());
        assertNull(sun.getEveningNight().getEnd());

        assertNull(sun.getNight().getStart());
        assertNull(sun.getNight().getEnd());
        
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 9, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_June_21_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_June_21_after_true_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 15, TIME_ZONE); // for sure after true
                                                                                          // midnight
        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 3, 23, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 3, 23, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 24, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 24, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 29, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 4, 29, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 50, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 13, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 13, 10, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 50, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 55, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 21, 55, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 22, 56, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show nautic dusk from 21th/22th
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 22, 56, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 22, 1, 9, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getAstroDusk().getStart());
        assertNull(sun.getAstroDusk().getEnd());

        assertNull(sun.getMorningNight().getStart());
        assertNull(sun.getMorningNight().getEnd());

        assertNull(sun.getEveningNight().getStart());
        assertNull(sun.getEveningNight().getEnd());

        assertNull(sun.getNight().getStart());
        assertNull(sun.getNight().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 9, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_June_21_after_true_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 1, 15, TIME_ZONE), LATITUDE, LONGITUDE);
    }
}
