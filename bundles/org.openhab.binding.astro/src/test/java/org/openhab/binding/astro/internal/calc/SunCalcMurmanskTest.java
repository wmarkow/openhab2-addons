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
 * Tests the sun daily events for Murmansk location. In particular it checks:
 * <ul>
 * <li>a full daylight at 21 June</li>
 * <li>a civil twilight and daylight at 21 May</li>
 * 
 * </ul>
 * 
 * @author Witold Markowski
 * @see <a href="https://www.timeanddate.com/sun/russia/murmansk">Sunrise and
 *      sunset times in Murmansk</a>
 */
public class SunCalcMurmanskTest extends AbstractSunCalcLocationTest {

    private final static double LATITUDE = 68.9368528;
    private final static double LONGITUDE = 33.0454321;
    private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Moscow");

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

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        // according to https://www.timeanddate.com/sun/russia/murmansk
        // the DAYLIGHT starts on 2019 MAY 21 00:16 local time but this is a moment of
        // SUN RISE start
        // In Astro DAYLIGHT starts when SUN RISE ends.
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 24, 1, 5, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // according to https://www.timeanddate.com/sun/russia/murmansk
        // the DAYLIGHT ends on 2019 JULY 21 00:45 local time but this is a moment of
        // SUN SET end
        // In Astro DAYLIGHT ends when SUN SET starts
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 21, 0, 47, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 12, 49, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 12, 50, TIME_ZONE).getTimeInMillis(),
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

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 49, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_June_21_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_May_21_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.MAY, 21, 0, 0, TIME_ZONE);

        Sun sun = subject.getSunInfo(date, LATITUDE, LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 0, 44, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 1, 11, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 1, 11, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // it looks like the rise duration is around 35 minutes here
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 1, 46, TIME_ZONE).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 1, 46, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // FIXME: Astro calculates a DAYLIGHT END here (however it is not true):
        // probably it is because of a low solar accuracy formulas used in Astro or
        // because in Astro DAYLIGHT end when Sun set starts
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 23, 46, TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 12, 44, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 12, 45, TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // FIXME: Astro calculates a SUN_SET here (however it is not true):
        // probably it is because of a low solar accuracy formulas used in Astro
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 23, 46, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // it looks like the rise duration is around 34 minutes here
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 22, 0, 20, TIME_ZONE).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // we are between MIDNIGHT and TRUE MIDNIGHT, show DUSK from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 0, 0, TIME_ZONE).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 0, 44, TIME_ZONE).getTimeInMillis(),
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

        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 21, 0, 44, TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_May_21_at_midnight() {
        testRangesCoherence(TestUtils.newCalendar(2019, Calendar.MAY, 21, 0, 0, TIME_ZONE), LATITUDE, LONGITUDE);
    }
}
