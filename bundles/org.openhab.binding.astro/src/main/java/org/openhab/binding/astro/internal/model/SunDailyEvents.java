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

/***
 * A set of Sun dayly events, like rise, set, transit, dawn, dusk, etc.
 * 
 * @author Witold Markowski
 *
 */
public class SunDailyEvents {
    public double transit;

    public double setStart;
    
    /***
     * Moment when the upper limb of the Sun disappears below the horizon
     */
    public double setEnd;

    /***
     * Moment when the upper limb of the Sun appears on the horizon in the morning.
     */
    public double riseStart;
    public double riseEnd;

    public double nauticDuskStart;
    public double civilDawnStart;
    public double astroDuskStart;
    public double nightStart;
    public double nauticDawnStart;
    public double astroDawnStart;
}
