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
 * <li>daily ranges at 27 February</li>
 * </ul>
 * 
 * @author Witold Markowski
 * @see <a href="https://github.com/openhab/openhab2-addons/issues/5006">[astro]
 *      Sun Phase returns UNDEF</a>
 * @see <a href="https://www.timeanddate.com/sun/netherlands/amsterdam">Sunrise
 *      and sunset times in Amsterdam</a>
 */
public class SunCalcAmsterdamTest extends AbstractSunCalcLocationTest {

    private final static double LATITUDE = 52.367607;
    private final static double LONGITUDE = 4.8978293;
    private final static double ALTITUDE = 0.0;
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Amsterdam");
    private final static Calendar FEB_27_2019 = TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 0, 59, TIME_ZONE);

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_Feb_27_after_true_midnight() {
        Sun sun = subject.getSunInfo(FEB_27_2019, LATITUDE, LONGITUDE, ALTITUDE);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 38, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 6, 18, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 6, 18, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 6, 57, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 6, 57, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 7, 31, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 7, 31, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 7, 35, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 7, 35, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 18, 11, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 12, 53, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 12, 53, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 18, 11, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 18, 15, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 18, 15, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 18, 49, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 18, 49, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 19, 29, TIME_ZONE).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 19, 29, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 20, 8, TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 27th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 0, 53, TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 38, TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 27th/28th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 20, 8, TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 28, 0, 53, TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show night from 26th/27th
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 26, 20, 6, TIME_ZONE).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 5, 38, TIME_ZONE).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.FEBRUARY, 27, 0, 53, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherence() {
        testRangesCoherence(FEB_27_2019, LATITUDE, LONGITUDE);
    }
}
