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
import java.util.GregorianCalendar;

import org.junit.Test;
import org.openhab.binding.astro.internal.model.Sun;
import org.openhab.binding.astro.internal.model.SunPhaseName;

public abstract class AbstractSunCalcLocationTest {
    protected final static int NOON_ACCURACY_IN_MILLIS = 5 * 60 * 1000;
    protected final static int MIDNIGHT_ACCURACY_IN_MILLIS = 5 * 60 * 1000;

    protected void testRangesCoherence(Calendar dateTime, double latitude, double longitude) {
        SunCalc subject = new SunCalc();
        Sun sun = subject.getSunInfo(dateTime, latitude, longitude, 0.0);

        if (sun.getMorningNight().getEnd() != null) {
            assertEquals(sun.getMorningNight().getEnd(), sun.getAstroDawn().getStart());
        }
        if (sun.getAstroDawn().getEnd() != null) {
            assertEquals(sun.getAstroDawn().getEnd(), sun.getNauticDawn().getStart());
        }
        if (sun.getNauticDawn().getEnd() != null) {
            assertEquals(sun.getNauticDawn().getEnd(), sun.getCivilDawn().getStart());
        }
        if (sun.getCivilDawn().getEnd() != null) {
            assertEquals(sun.getCivilDawn().getEnd(), sun.getRise().getStart());
        }
        if (sun.getRise().getEnd() != null) {
            assertEquals(sun.getRise().getEnd(), sun.getDaylight().getStart());
        }
        if (sun.getDaylight().getEnd() != null) {
            assertEquals(sun.getDaylight().getEnd(), sun.getSet().getStart());
        }
        if (sun.getSet().getEnd() != null) {
            assertEquals(sun.getSet().getEnd(), sun.getCivilDusk().getStart());
        }
        if (sun.getCivilDusk().getEnd() != null) {
            assertEquals(sun.getCivilDusk().getEnd(), sun.getNauticDusk().getStart());
        }
        if (sun.getNauticDusk().getEnd() != null) {
            assertEquals(sun.getNauticDusk().getEnd(), sun.getAstroDusk().getStart());
        }
        if (sun.getAstroDusk().getEnd() != null) {
            if (sun.getEveningNight().getStart() != null) {
                // evening night is present
                assertEquals(sun.getAstroDusk().getEnd(), sun.getEveningNight().getStart());
            }
            else
            {
                // it must end at the next true midnight
            }
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
