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
package org.openhab.binding.astro.internal.model;

import java.util.Calendar;

/***
 * A set of Sun daily events, like rise, set, transit, dawn, dusk, etc.
 * 
 * @author Witold Markowski
 *
 */
public class SunDailyEvents {
    public Calendar transit;

    public Calendar setStart;

    /***
     * Moment when the upper limb of the Sun disappears below the horizon
     */
    public Calendar setEnd;

    /***
     * Moment when the upper limb of the Sun appears on the horizon in the morning.
     */
    public Calendar riseStart;
    public Calendar riseEnd;

    public Calendar nauticDuskStart;
    public Calendar civilDawnStart;
    public Calendar astroDuskStart;
    public Calendar nightStart;
    public Calendar nauticDawnStart;
    public Calendar astroDawnStart;
    public Calendar trueMidnight;
    public Calendar nextTrueMidnight;

    public Range getMorningNightRange() {
        if (astroDawnStart != null) {
            return new Range(trueMidnight, astroDawnStart);
        }

        return new Range();
    }

    public Range getAstroDawnRange() {
        if (astroDawnStart != null && nauticDawnStart != null) {
            return new Range(astroDawnStart, nauticDawnStart);
        }

        if (astroDawnStart == null && nauticDawnStart != null) {
            return new Range(trueMidnight, nauticDawnStart);
        }

        if (astroDawnStart != null && nauticDawnStart == null) {
            return new Range(astroDawnStart, transit);
        }

        return new Range();
    }

    public Range getNauticDawnRange() {
        if (nauticDawnStart != null && civilDawnStart != null) {
            return new Range(nauticDawnStart, civilDawnStart);
        }

        if (nauticDawnStart == null && civilDawnStart != null) {
            return new Range(trueMidnight, civilDawnStart);
        }

        if (nauticDawnStart != null && civilDawnStart == null) {
            return new Range(nauticDawnStart, transit);
        }

        return new Range();
    }

    public Range getCivilDawnRange() {
        if (civilDawnStart != null && riseStart != null) {
            return new Range(civilDawnStart, riseStart);
        }

        if (civilDawnStart == null && riseStart != null) {
            return new Range(trueMidnight, riseStart);
        }

        if (civilDawnStart != null && riseStart == null) {
            return new Range(civilDawnStart, transit);
        }

        return new Range();
    }

    public Range getRiseRange() {
        if (riseStart != null && riseEnd != null) {
            return new Range(riseStart, riseEnd);
        }

        return new Range();
    }

    public Range getDaylightRange() {
        if (riseEnd != null && setStart != null) {
            return new Range(riseEnd, setStart);
        }

        return new Range();
    }

    public Range getNoonRange() {
        if (transit != null) {
            final Calendar transitEnd = ((Calendar) transit.clone());
            transitEnd.add(Calendar.MINUTE, 1);

            return new Range(transit, transitEnd);
        }
        return new Range();
    }

    public Range getSetRange() {
        if (setStart != null && setEnd != null) {
            return new Range(setStart, setEnd);
        }

        return new Range();
    }

    public Range getCivilDuskRange() {
        if (setEnd != null && nauticDuskStart != null) {
            return new Range(setEnd, nauticDuskStart);
        }

        if (setEnd != null && nauticDuskStart == null) {
            return new Range(setEnd, nextTrueMidnight);
        }

        if (setEnd == null && nauticDuskStart != null) {
            return new Range(transit, nauticDuskStart);
        }

        return new Range();
    }

    public Range getNauticDuskRange() {
        if (nauticDuskStart != null && astroDuskStart != null) {
            return new Range(nauticDuskStart, astroDuskStart);
        }

        if (nauticDuskStart != null && astroDuskStart == null) {
            return new Range(nauticDuskStart, nextTrueMidnight);
        }

        if (nauticDuskStart == null && astroDuskStart != null) {
            return new Range(transit, astroDuskStart);
        }

        return new Range();
    }

    public Range getAstroDuskRange() {
        if (astroDuskStart != null && nightStart != null) {
            return new Range(astroDuskStart, nightStart);
        }

        if (astroDuskStart != null && nightStart == null) {
            return new Range(astroDuskStart, nextTrueMidnight);
        }

        if (astroDuskStart == null && nightStart != null) {
            return new Range(transit, nightStart);
        }

        return new Range();
    }

    public Range getEveningNightRange() {
        if (nightStart != null) {
            return new Range(nightStart, nextTrueMidnight);
        }

        return new Range();
    }
}
