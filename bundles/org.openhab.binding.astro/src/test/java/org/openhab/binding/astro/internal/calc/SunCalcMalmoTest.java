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

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.astro.TestUtils;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunPhaseName;

public class SunCalcMalmoTest extends AbstractSunCalcLocationTest {

    private final static double MALMO_LATITUDE = 55.5700886;
    private final static double MALMO_LONGITUDE = 12.8758905;

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_June_21_at_midnight() {
        Calendar date = new GregorianCalendar(2019, Calendar.JUNE, 21, 0, 0);
        Sun sun = subject.getSunInfo(date, MALMO_LATITUDE, MALMO_LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 1, 9).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 3, 23).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 3, 23).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 24).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 24).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 29).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 29).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 50).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 13, 9).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 13, 10).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 50).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 55).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 55).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 22, 56).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show nautic dusk from 20th/21th
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 20, 22, 56).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 1, 9).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getAstroDusk().getStart());
        assertNull(sun.getAstroDusk().getEnd());

        assertNull(sun.getMorningNight().getStart());
        assertNull(sun.getMorningNight().getEnd());

        assertNull(sun.getEveningNight().getStart());
        assertNull(sun.getEveningNight().getEnd());

        assertNull(sun.getNight().getStart());
        assertNull(sun.getNight().getEnd());
    }

    @Test
    public void testRangesCoherenceFor_2019_June_21_at_midnight() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.JUNE, 21, 0, 0), MALMO_LATITUDE, MALMO_LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_June_21_after_true_midnight() {
        Calendar date = new GregorianCalendar(2019, Calendar.JUNE, 21, 1, 15); // for sure after true midnight
        Sun sun = subject.getSunInfo(date, MALMO_LATITUDE, MALMO_LONGITUDE, 0.0);

        assertNull(sun.getAstroDawn().getStart());
        assertNull(sun.getAstroDawn().getEnd());

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 1, 9).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 3, 23).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 3, 23).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 24).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 24).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 29).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 4, 29).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 50).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 13, 9).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 13, 10).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 50).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 55).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 21, 55).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 22, 56).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

     // show nautic dusk from 21th/22th
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 22, 56).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 22, 1, 9).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getAstroDusk().getStart());
        assertNull(sun.getAstroDusk().getEnd());

        assertNull(sun.getMorningNight().getStart());
        assertNull(sun.getMorningNight().getEnd());

        assertNull(sun.getEveningNight().getStart());
        assertNull(sun.getEveningNight().getEnd());

        assertNull(sun.getNight().getStart());
        assertNull(sun.getNight().getEnd());

        assertEquals(new GregorianCalendar(2019, Calendar.JUNE, 21, 1, 9).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_June_21_at_true_midnight() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.JUNE, 21, 1, 9), MALMO_LATITUDE, MALMO_LONGITUDE);
    }
}
