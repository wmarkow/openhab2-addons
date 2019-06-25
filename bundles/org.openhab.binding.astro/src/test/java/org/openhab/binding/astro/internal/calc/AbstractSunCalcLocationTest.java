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
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunPhaseName;

public abstract class AbstractSunCalcLocationTest {
    protected final static int NOON_ACCURACY_IN_MILLIS = 5 * 60 * 1000;
    protected final static int MIDNIGHT_ACCURACY_IN_MILLIS = 5 * 60 * 1000;

    protected void testRangesCoherence(Calendar dateTime, double latitude, double longitude) {
        testRangesCoherenceForNotNullValues(dateTime, latitude, longitude);
        testRangesCoherenceForContinuity(dateTime, latitude, longitude);
    }

    protected void testRangesCoherenceForNotNullValues(Calendar dateTime, double latitude, double longitude) {
        SunCalc subject = new SunCalc();
        Sun sun = subject.getSunInfo(dateTime, latitude, longitude, 0.0);

        if (sun.getMorningNight().getStart() == null ^ sun.getMorningNight().getEnd() == null) {
            fail("Start or End of MORNING_NIGHT is null");
        }
        if (sun.getAstroDawn().getStart() == null ^ sun.getAstroDawn().getEnd() == null) {
            fail("Start or End of ASTRO_DOWN is null");
        }
        if (sun.getNauticDawn().getStart() == null ^ sun.getNauticDawn().getEnd() == null) {
            fail("Start or End of NAUTIC_DAWN is null");
        }
        if (sun.getCivilDawn().getStart() == null ^ sun.getCivilDawn().getEnd() == null) {
            fail("Start or End of CIVIL_DAWN is null");
        }
        if (sun.getRise().getStart() == null ^ sun.getRise().getEnd() == null) {
            fail("Start or End of RISE is null");
        }
        if (sun.getDaylight().getStart() == null ^ sun.getDaylight().getEnd() == null) {
            fail("Start or End of DAYLIGHT is null");
        }
        if (sun.getNoon().getStart() == null ^ sun.getNoon().getEnd() == null) {
            fail("Start or End of NOON is null");
        }
        if (sun.getSet().getStart() == null ^ sun.getSet().getEnd() == null) {
            fail("Start or End of SET is null");
        }
        if (sun.getCivilDusk().getStart() == null ^ sun.getCivilDusk().getEnd() == null) {
            fail("Start or End of CIVIL_DUSK is null");
        }
        if (sun.getNauticDusk().getStart() == null ^ sun.getNauticDusk().getEnd() == null) {
            fail("Start or End of NAUTIC_DUSK is null");
        }
        if (sun.getAstroDusk().getStart() == null ^ sun.getAstroDusk().getEnd() == null) {
            fail("Start or End of ASTRO_DUSK is null");
        }
        if (sun.getEveningNight().getStart() == null ^ sun.getEveningNight().getEnd() == null) {
            fail("Start or End of EVENING_NIGHT is null");
        }
        if (sun.getNight().getStart() == null ^ sun.getNight().getEnd() == null) {
            fail("Start or End of NIGHT is null");
        }
    }

    private void testRangesCoherenceForContinuity(Calendar dateTime, double latitude, double longitude) {
        SunCalc subject = new SunCalc();
        Sun sun = subject.getSunInfo(dateTime, latitude, longitude, 0.0);

        if (sun.getMorningNight().isBounded()) {
            assertEquals(sun.getMorningNight().getEnd(), sun.getAstroDawn().getStart());
            assertEquals(sun.getMorningNight().getStart(), sun.getTrueMidnight());
        }
        if (sun.getAstroDawn().isBounded()) {
            assertEquals(sun.getAstroDawn().getEnd(), sun.getNauticDawn().getStart());
        }
        if (sun.getAstroDawn().isBounded() && !sun.getMorningNight().isBounded()) {
            assertEquals(sun.getAstroDawn().getStart(), sun.getTrueMidnight());
        }
        if (sun.getNauticDawn().isBounded()) {
            assertEquals(sun.getNauticDawn().getEnd(), sun.getCivilDawn().getStart());
        }
        if (sun.getNauticDawn().isBounded() && !sun.getAstroDawn().isBounded()) {
            assertEquals(sun.getNauticDawn().getEnd(), sun.getTrueMidnight());
        }
        if (sun.getCivilDawn().isBounded()) {
            assertEquals(sun.getCivilDawn().getEnd(), sun.getRise().getStart());
        }
        if (sun.getCivilDawn().isBounded() && !sun.getNauticDawn().isBounded()) {
            assertEquals(sun.getCivilDawn().getEnd(), sun.getTrueMidnight());
        }
        if (sun.getRise().isBounded()) {
            assertEquals(sun.getRise().getEnd(), sun.getDaylight().getStart());
        }
        if (sun.getDaylight().isBounded()) {
            assertEquals(sun.getDaylight().getEnd(), sun.getSet().getStart());
        }
        if (sun.getSet().isBounded()) {
            assertEquals(sun.getSet().getEnd(), sun.getCivilDusk().getStart());
        }
        if (sun.getCivilDusk().isBounded()) {
            if (sun.getNauticDusk().isBounded()) {
                assertEquals(sun.getCivilDusk().getEnd(), sun.getNauticDusk().getStart());
            } else {
                // nautic dusk not present, so civil dusk must end at next true midnight
                assertEquals(sun.getCivilDusk().getEnd(), sun.getNextTrueMidnight());
            }
        }
        if (sun.getNauticDusk().isBounded()) {
            if (sun.getAstroDusk().isBounded()) {
                assertEquals(sun.getNauticDusk().getEnd(), sun.getAstroDusk().getStart());
            } else {
                // astro dusk not present, so nautic dusk must end at next true midnight
                assertEquals(sun.getNauticDusk().getEnd(), sun.getNextTrueMidnight());
            }
        }
        if (sun.getAstroDusk().isBounded()) {
            if (sun.getEveningNight().isBounded()) {
                // evening night is present
                assertEquals(sun.getAstroDusk().getEnd(), sun.getEveningNight().getStart());
            } else {
                // evening night nopt present, so astro dusk must end at the next true midnight
                assertEquals(sun.getAstroDusk().getEnd(), sun.getNextTrueMidnight());
            }
        }
        if (sun.getEveningNight().isBounded()) {
            // if evening night exist then it must end at next true midnight
            assertEquals(sun.getEveningNight().getEnd(), sun.getNextTrueMidnight());
        }
    }

    protected void testGetAllRanges(Calendar dateTime, double latitude, double longitude) {
        SunCalc subject = new SunCalc();
        Sun sun = subject.getSunInfo(dateTime, latitude, longitude, 0.0);

        assertEquals(13, sun.getAllRanges().size());
        assertEquals(sun.getAstroDawn(), sun.getAllRanges().get(SunPhaseName.ASTRO_DAWN));
        assertEquals(sun.getNauticDawn(), sun.getAllRanges().get(SunPhaseName.NAUTIC_DAWN));
        assertEquals(sun.getCivilDawn(), sun.getAllRanges().get(SunPhaseName.CIVIL_DAWN));
        assertEquals(sun.getRise(), sun.getAllRanges().get(SunPhaseName.SUN_RISE));
        assertEquals(sun.getDaylight(), sun.getAllRanges().get(SunPhaseName.DAYLIGHT));
        assertEquals(sun.getNoon(), sun.getAllRanges().get(SunPhaseName.NOON));
        assertEquals(sun.getSet(), sun.getAllRanges().get(SunPhaseName.SUN_SET));
        assertEquals(sun.getCivilDusk(), sun.getAllRanges().get(SunPhaseName.CIVIL_DUSK));
        assertEquals(sun.getNauticDusk(), sun.getAllRanges().get(SunPhaseName.NAUTIC_DUSK));
        assertEquals(sun.getAstroDusk(), sun.getAllRanges().get(SunPhaseName.ASTRO_DUSK));
        assertEquals(sun.getNight(), sun.getAllRanges().get(SunPhaseName.NIGHT));
        assertEquals(sun.getMorningNight(), sun.getAllRanges().get(SunPhaseName.MORNING_NIGHT));
        assertEquals(sun.getEveningNight(), sun.getAllRanges().get(SunPhaseName.EVENING_NIGHT));
    }
}
