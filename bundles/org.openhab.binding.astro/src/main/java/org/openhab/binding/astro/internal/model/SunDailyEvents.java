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
}
