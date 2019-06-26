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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunPhaseName;

/***
 * Specific unit tests to check if {@link SunCalc} generates correct data for
 * Amsterdam city on 27 February 2019. In particular the following cases are
 * covered:
 * <ul>
 * <li>checks if generated data are the same (with some accuracy) as produced by
 * haevens-above.com</li>
 * <li>checks if the generated {@link Sun#getAllRanges()} are consistent with
 * each other</li>
 * </ul>
 * 
 * @author Witold Markowski
 * @see <a href="https://github.com/openhab/openhab2-addons/issues/5006">[astro]
 *      Sun Phase returns UNDEF</a>
 * @see <a href="https://www.heavens-above.com/sun.aspx">Heavens Above Sun</a>
 */
public class SunCalcAmsterdamTest extends AbstractSunCalcLocationTest {

    private final static Calendar FEB_27_2019 = new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 0, 55);
    private final static double AMSTERDAM_LATITUDE = 52.367607;
    private final static double AMSTERDAM_LONGITUDE = 4.8978293;
    private final static double AMSTERDAM_ALTITUDE = 0.0;
    private final static int ACCURACY_IN_MILLIS = 3 * 60 * 1000;

    private SunCalc subject;

    @Before
    public void init() {
        FEB_27_2019.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
        subject = new SunCalc();
    }

    @Test
    public void testGetSunInfoForOldDate() {
        Calendar calendar = new GregorianCalendar(2019, Calendar.FEBRUARY, 27);
        TimeZone.getAvailableIDs();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));

        Sun sun = subject.getSunInfo(calendar, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        assertNotNull(sun.getNight());

        assertNotNull(sun.getAstroDawn());
        assertNotNull(sun.getNauticDawn());
        assertNotNull(sun.getCivilDawn());

        assertNotNull(sun.getRise());

        assertNotNull(sun.getDaylight());
        assertNotNull(sun.getNoon());
        assertNotNull(sun.getSet());

        assertNotNull(sun.getCivilDusk());
        assertNotNull(sun.getNauticDusk());
        assertNotNull(sun.getAstroDusk());
        assertNotNull(sun.getNight());

        assertNotNull(sun.getMorningNight());
        assertNotNull(sun.getEveningNight());

        // for an old date the phase is always null
        assertNull(sun.getPhase().getName());
    }

    @Test
    public void testGetSunInfoForAstronomicalDawnAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 05:39 till 06:18
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 5, 39).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 6, 18).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForNauticDawnAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 06:18 till 06:58
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 6, 18).getTimeInMillis(),
                sun.getNauticDawn().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 6, 58).getTimeInMillis(),
                sun.getNauticDawn().getEnd().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForCivilDawnAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 06:58 till 07:32
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 6, 58).getTimeInMillis(),
                sun.getCivilDawn().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 7, 32).getTimeInMillis(),
                sun.getCivilDawn().getEnd().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForRiseAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 07:32
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 7, 32).getTimeInMillis(),
                sun.getRise().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForSunNoonAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 12:54
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 12, 54).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForSetAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 18:15
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 18, 15).getTimeInMillis(),
                sun.getSet().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForCivilDuskAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 18:15 till 18:50
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 18, 15).getTimeInMillis(),
                sun.getCivilDusk().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 18, 50).getTimeInMillis(),
                sun.getCivilDusk().getEnd().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForNauticDuskAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 18:50 till 19:29
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 18, 50).getTimeInMillis(),
                sun.getNauticDusk().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 19, 29).getTimeInMillis(),
                sun.getNauticDusk().getEnd().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testGetSunInfoForAstronomicalDuskAccuracy() {
        Sun sun = subject.getSunInfo(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE, AMSTERDAM_ALTITUDE);

        // expected result from haevens-above.com is 27 Feb 2019 19:29 till 20:09
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 19, 29).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), ACCURACY_IN_MILLIS);
        assertEquals(new GregorianCalendar(2019, Calendar.FEBRUARY, 27, 20, 9).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherence() {
        testRangesCoherence(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE);
    }

    @Test
    public void testGetAllRanges() {
        testGetAllRanges(FEB_27_2019, AMSTERDAM_LATITUDE, AMSTERDAM_LONGITUDE);
    }
}
