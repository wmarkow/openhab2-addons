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
package org.openhab.binding.astro.internal;

import static org.junit.Assert.assertEquals;

import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.junit.Test;

public class AstroBindingConstantsTest {

    @Test
    public void testBasics() {
        assertEquals("astro", AstroBindingConstants.BINDING_ID);
        assertEquals("sun", AstroBindingConstants.SUN);
        assertEquals("moon", AstroBindingConstants.MOON);
        assertEquals("local", AstroBindingConstants.LOCAL);
    }

    @Test
    public void testThingsTypes() {
        ThingTypeUID thingTypeSun = new ThingTypeUID(AstroBindingConstants.BINDING_ID, AstroBindingConstants.SUN);
        ThingTypeUID thingTypeMoon = new ThingTypeUID(AstroBindingConstants.BINDING_ID, AstroBindingConstants.MOON);

        assertEquals(thingTypeSun, AstroBindingConstants.THING_TYPE_SUN);
        assertEquals(thingTypeMoon, AstroBindingConstants.THING_TYPE_MOON);
    }

    @Test
    public void testEventsNames() {
        assertEquals("START", AstroBindingConstants.EVENT_START);
        assertEquals("END", AstroBindingConstants.EVENT_END);

        assertEquals("FIRST_QUARTER", AstroBindingConstants.EVENT_PHASE_FIRST_QUARTER);
        assertEquals("THIRD_QUARTER", AstroBindingConstants.EVENT_PHASE_THIRD_QUARTER);
        assertEquals("FULL", AstroBindingConstants.EVENT_PHASE_FULL);
        assertEquals("NEW", AstroBindingConstants.EVENT_PHASE_NEW);

        assertEquals("PARTIAL", AstroBindingConstants.EVENT_ECLIPSE_PARTIAL);
        assertEquals("TOTAL", AstroBindingConstants.EVENT_ECLIPSE_TOTAL);
        assertEquals("RING", AstroBindingConstants.EVENT_ECLIPSE_RING);

        assertEquals("PERIGEE", AstroBindingConstants.EVENT_PERIGEE);
        assertEquals("APOGEE", AstroBindingConstants.EVENT_APOGEE);
    }

    @Test
    public void testChannelsIds() {
        assertEquals("phase#event", AstroBindingConstants.EVENT_CHANNEL_ID_MOON_PHASE);
        assertEquals("eclipse#event", AstroBindingConstants.EVENT_CHANNEL_ID_ECLIPSE);
        assertEquals("perigee#event", AstroBindingConstants.EVENT_CHANNEL_ID_PERIGEE);
        assertEquals("apogee#event", AstroBindingConstants.EVENT_CHANNEL_ID_APOGEE);

        assertEquals("rise#event", AstroBindingConstants.EVENT_CHANNEL_ID_RISE);
        assertEquals("set#event", AstroBindingConstants.EVENT_CHANNEL_ID_SET);
        assertEquals("noon#event", AstroBindingConstants.EVENT_CHANNEL_ID_NOON);
        assertEquals("night#event", AstroBindingConstants.EVENT_CHANNEL_ID_NIGHT);
        assertEquals("morningNight#event", AstroBindingConstants.EVENT_CHANNEL_ID_MORNING_NIGHT);
        assertEquals("astroDawn#event", AstroBindingConstants.EVENT_CHANNEL_ID_ASTRO_DAWN);
        assertEquals("nauticDawn#event", AstroBindingConstants.EVENT_CHANNEL_ID_NAUTIC_DAWN);
        assertEquals("civilDawn#event", AstroBindingConstants.EVENT_CHANNEL_ID_CIVIL_DAWN);
        assertEquals("astroDusk#event", AstroBindingConstants.EVENT_CHANNEL_ID_ASTRO_DUSK);
        assertEquals("nauticDusk#event", AstroBindingConstants.EVENT_CHANNEL_ID_NAUTIC_DUSK);
        assertEquals("civilDusk#event", AstroBindingConstants.EVENT_CHANNEL_ID_CIVIL_DUSK);
        assertEquals("eveningNight#event", AstroBindingConstants.EVENT_CHANNEL_ID_EVENING_NIGHT);
        assertEquals("daylight#event", AstroBindingConstants.EVENT_CHANNEL_ID_DAYLIGHT);

        assertEquals("phase#name", AstroBindingConstants.CHANNEL_ID_SUN_PHASE_NAME);
    }
}
