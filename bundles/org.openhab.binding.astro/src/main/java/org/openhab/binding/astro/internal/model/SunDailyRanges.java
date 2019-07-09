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
 * A set of Sun daily ranges, like rise range, set range, transit range, dawn
 * range, dusk range, etc.
 * 
 * @author Witold Markowski
 *
 */
public class SunDailyRanges {

    private Range morningNight;
    private Range astroDawn;
    private Range nauticDawn;
    private Range civilDawn;
    private Range rise;
    private Range daylight;
    private Range noon;
    private Range set;
    private Range civilDusk;
    private Range nauticDusk;
    private Range astroDusk;
    private Range eveningNight;
    private Range night;

    private Calendar trueMidnight;
    private Calendar nextTrueMidnight;

    public Range getMorningNight() {
        return morningNight;
    }

    public void setMorningNight(Range morningNight) {
        this.morningNight = morningNight;
    }

    public Range getAstroDawn() {
        return astroDawn;
    }

    public void setAstroDawn(Range astroDawn) {
        this.astroDawn = astroDawn;
    }

    public Range getNauticDawn() {
        return nauticDawn;
    }

    public void setNauticDawn(Range nauticDawn) {
        this.nauticDawn = nauticDawn;
    }

    public Range getCivilDawn() {
        return civilDawn;
    }

    public void setCivilDawn(Range civilDawn) {
        this.civilDawn = civilDawn;
    }

    public Range getRise() {
        return rise;
    }

    public void setRise(Range rise) {
        this.rise = rise;
    }

    public Range getDaylight() {
        return daylight;
    }

    public void setDaylight(Range daylight) {
        this.daylight = daylight;
    }

    public Range getNoon() {
        return noon;
    }

    public void setNoon(Range noon) {
        this.noon = noon;
    }

    public Range getSet() {
        return set;
    }

    public void setSet(Range set) {
        this.set = set;
    }

    public Range getCivilDusk() {
        return civilDusk;
    }

    public void setCivilDusk(Range civilDusk) {
        this.civilDusk = civilDusk;
    }

    public Range getNauticDusk() {
        return nauticDusk;
    }

    public void setNauticDusk(Range nauticDusk) {
        this.nauticDusk = nauticDusk;
    }

    public Range getAstroDusk() {
        return astroDusk;
    }

    public void setAstroDusk(Range astroDusk) {
        this.astroDusk = astroDusk;
    }

    public Range getEveningNight() {
        return eveningNight;
    }

    public void setEveningNight(Range eveningNight) {
        this.eveningNight = eveningNight;
    }

    public Range getNight() {
        return night;
    }

    public void setNight(Range night) {
        this.night = night;
    }

    public Calendar getTrueMidnight() {
        return trueMidnight;
    }

    public void setTrueMidnight(Calendar trueMidnight) {
        this.trueMidnight = trueMidnight;
    }

    public Calendar getNextTrueMidnight() {
        return nextTrueMidnight;
    }

    public void setNextTrueMidnight(Calendar nextTrueMidnight) {
        this.nextTrueMidnight = nextTrueMidnight;
    }
}
