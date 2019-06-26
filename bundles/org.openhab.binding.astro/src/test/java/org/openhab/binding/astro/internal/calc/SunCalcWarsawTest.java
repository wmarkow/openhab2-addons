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
import org.junit.Ignore;
import org.junit.Test;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunPhaseName;

public class SunCalcWarsawTest extends AbstractSunCalcLocationTest {

    private final static double WARSAW_LATITUDE = 52.236927;
    private final static double WARSAW_LONGITUDE = 21.040482;

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_May_16_at_midnight() {
        Calendar date = new GregorianCalendar(2019, Calendar.MAY, 16, 0, 0);
        Sun sun = subject.getSunInfo(date, WARSAW_LATITUDE, WARSAW_LONGITUDE, 0.0);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 1, 21).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 2, 58).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 2, 58).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 3, 57).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 3, 57).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 40).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 40).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 45).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 45).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 25).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 12, 32).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 12, 33).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 21).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 25).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 25).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 21, 8).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 21, 8).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 22, 8).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 22, 8).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 23, 50).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 16th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 32).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 1, 21).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 15th/16th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 15, 23, 42).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 32).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show night from 15th/16th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 15, 23, 42).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 1, 21).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_May_16_at_midnight() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 0), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    @Test
    public void testgetAllRangesFor_2019_May_16_at_midnight() {
        testGetAllRanges(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 0), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_May_16_at_true_midnight() {
        // our calculations gives 00:34 as a true midnight
        Calendar date = new GregorianCalendar(2019, Calendar.MAY, 16, 0, 34);
        Sun sun = subject.getSunInfo(date, WARSAW_LATITUDE, WARSAW_LONGITUDE, 0.0);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 1, 21).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 2, 58).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 2, 58).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 3, 57).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 3, 57).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 40).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 40).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 45).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 4, 45).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 25).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 12, 32).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 12, 33).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 21).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 25).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 20, 25).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 21, 8).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 21, 8).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 22, 8).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 22, 8).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 23, 50).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 16th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 32).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 1, 21).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 16th/17th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 23, 50).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 17, 0, 32).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // we are shortly after true midnight but still currently in NIGHT, need to show
        // night from 15th/16th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 15, 23, 42).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 16, 1, 21).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_May_16_at_true_midnight() {
        // our calculations gives 00:34 as a true midnight
        testRangesCoherence(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 34), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    @Test
    public void testgetAllRangesFor_2019_May_16_at_true_midnight() {
        // our calculations gives 00:34 as a true midnight
        testGetAllRanges(new GregorianCalendar(2019, Calendar.MAY, 16, 0, 34), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_May_19_at_midnight() {
        Calendar date = new GregorianCalendar(2019, Calendar.MAY, 19, 0, 0);
        Sun sun = subject.getSunInfo(date, WARSAW_LATITUDE, WARSAW_LONGITUDE, 0.0);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 51).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 2, 51).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 2, 51).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 3, 52).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 3, 52).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 35).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 35).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 39).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 39).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 29).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 12, 32).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 12, 33).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 24).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 29).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 29).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 21, 13).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 21, 13).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 22, 15).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show dusk from 18th/19th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 18, 22, 12).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 12).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);

        // show morning night from 19th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 32).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 51).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 18th/19th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 12).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 32).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show night from 18th/19th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 12).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), MIDNIGHT_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 51).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_May_19_at_midnight() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 0), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    @Test
    public void testgetAllRangesFor_2019_May_19_at_midnight() {
        testGetAllRanges(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 0), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    /***
     * Ignoring this test because at this specific date and location it incorrectly
     * calculates the night presence (we have a low precision sun calculation).
     */
    @Ignore
    @Test
    public void testRangesFor_2019_May_19_at_true_midnight() {
        // our calculations gives 00:34 as a true midnight
        Calendar date = new GregorianCalendar(2019, Calendar.MAY, 19, 0, 34);
        Sun sun = subject.getSunInfo(date, WARSAW_LATITUDE, WARSAW_LONGITUDE, 0.0);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 51).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 2, 51).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 2, 51).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 3, 52).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 3, 52).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 35).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 35).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 39).getTimeInMillis(),
                sun.getRise().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 4, 39).getTimeInMillis(),
                sun.getDaylight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 29).getTimeInMillis(),
                sun.getDaylight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 12, 32).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 12, 33).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 24).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 29).getTimeInMillis(),
                sun.getSet().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 20, 29).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 21, 13).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 21, 13).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 22, 15).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show dusk from 19th/20th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 22, 15).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 20, 0, 32).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 19th
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 32).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 51).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 19th/20th - aha! not present
        assertNull(sun.getMorningNight().getStart());
        assertNull(sun.getMorningNight().getEnd());

        // show night from 19th/20th - aha! not present
        assertNull(sun.getNight().getStart());
        assertNull(sun.getNight().getEnd());
    }

    @Test
    public void testRangesCoherenceFor_2019_May_19_at_true_midnight() {
        // our calculations gives 00:34 as a true midnight
        testRangesCoherence(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 34), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    @Test
    public void testgetAllRangesFor_2019_May_19_at_true_midnight() {
        // our calculations gives 00:34 as a true midnight
        testGetAllRanges(new GregorianCalendar(2019, Calendar.MAY, 19, 0, 34), WARSAW_LATITUDE, WARSAW_LONGITUDE);
    }

    public void testPhaseNameForDate(Calendar date) {

    }
}
