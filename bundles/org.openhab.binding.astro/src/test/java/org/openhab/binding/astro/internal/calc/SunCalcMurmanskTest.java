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
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.astro.TestUtils;
import org.openhab.binding.astro.internal.model.Sun;

/***
 * Tests the sun daily events for Murmansk city. In particular it tests how
 * Astro produces the sun daily events on June 21 when in Murmansk a full
 * daylight is present.
 * 
 * @author Witold Markowski
 *
 */
public class SunCalcMurmanskTest extends AbstractSunCalcLocationTest {

    private final static double MURMANSK_LATITUDE = 68.9368528;
    private final static double MURMANSK_LONGITUDE = 33.0454321;
    private final static TimeZone MOSCOW_TIME_ZONE = TimeZone.getTimeZone("Europe/Moscow");

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_June_21_at_midnight() {
        Calendar date = new GregorianCalendar(2019, Calendar.JUNE, 21, 0, 0);
        date.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        Sun sun = subject.getSunInfo(date, MURMANSK_LATITUDE, MURMANSK_LONGITUDE, 0.0);

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
        assertEquals(TestUtils.newCalendar(2019, Calendar.MAY, 24, 1, 5, MOSCOW_TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        // according to https://www.timeanddate.com/sun/russia/murmansk
        // the DAYLIGHT ends on 2019 JULY 21 00:45 local time but this is a moment of
        // SUN SET end
        // In Astro DAYLIGHT ends when SUN SET starts
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 21, 0, 47, MOSCOW_TIME_ZONE).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 12, 49, MOSCOW_TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 12, 50, MOSCOW_TIME_ZONE).getTimeInMillis(),
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
        
        assertEquals(TestUtils.newCalendar(2019, Calendar.JUNE, 21, 0, 49, MOSCOW_TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_June_21_at_midnight() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.JUNE, 21, 0, 0), MURMANSK_LATITUDE,
                MURMANSK_LONGITUDE);
    }
}
