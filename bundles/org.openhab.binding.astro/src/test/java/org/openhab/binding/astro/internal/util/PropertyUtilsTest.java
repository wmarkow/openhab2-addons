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
package org.openhab.binding.astro.internal.util;

import org.eclipse.smarthome.core.common.AbstractUID;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.junit.Test;
import org.openhab.binding.astro.internal.AstroBindingConstants;
import org.openhab.binding.astro.internal.model.Moon;
import org.openhab.binding.astro.internal.model.Range;
import org.openhab.binding.astro.internal.model.Sun;

public class PropertyUtilsTest {

    @Test
    public void testGetPropertyValueForSunChannels() throws Exception {
        Sun sun = new Sun();
        sun.setNoon(new Range());
        sun.setAstroDawn(new Range());
        sun.setAstroDusk(new Range());
        sun.setCivilDawn(new Range());
        sun.setCivilDusk(new Range());
        sun.setDaylight(new Range());
        sun.setEveningNight(new Range());
        sun.setMorningNight(new Range());
        sun.setNauticDawn(new Range());
        sun.setNauticDusk(new Range());
        sun.setNight(new Range());

        PropertyUtils.getPropertyValue(createSunChannelUID("rise#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("rise#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("rise#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("set#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("set#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("set#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("noon#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("noon#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("noon#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("night#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("night#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("night#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("morningNight#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("morningNight#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("morningNight#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("astroDawn#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("astroDawn#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("astroDawn#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("nauticDawn#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("nauticDawn#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("nauticDawn#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("civilDawn#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("civilDawn#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("civilDawn#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("astroDusk#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("astroDusk#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("astroDusk#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("nauticDusk#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("nauticDusk#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("nauticDusk#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("civilDusk#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("civilDusk#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("civilDusk#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("eveningNight#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("eveningNight#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("eveningNight#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("daylight#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("daylight#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("daylight#duration"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("position#azimuth"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("position#elevation"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("position#shadeLength"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("radiation#direct"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("radiation#diffuse"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("radiation#total"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("zodiac#start"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("zodiac#end"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("zodiac#sign"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("season#name"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("eclipse#total"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("eclipse#partial"), sun);
        PropertyUtils.getPropertyValue(createSunChannelUID("eclipse#ring"), sun);

        PropertyUtils.getPropertyValue(createSunChannelUID("phase#name"), sun);
    }

    @Test
    public void testGetPropertyValueForMoonChannels() throws Exception {
        Moon moon = new Moon();

        PropertyUtils.getPropertyValue(createMoonChannelUID("rise#start"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("rise#end"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("rise#duration"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("set#start"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("set#end"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("set#duration"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#firstQuarter"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#thirdQuarter"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#full"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#new"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#age"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#ageDegree"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#agePercent"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#illumination"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("phase#name"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("eclipse#total"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("eclipse#partial"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("distance#date"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("distance#distance"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("perigee#date"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("perigee#distance"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("apogee#date"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("apogee#distance"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("position#azimuth"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("position#elevation"), moon);
        PropertyUtils.getPropertyValue(createMoonChannelUID("position#shadeLength"), moon);

        PropertyUtils.getPropertyValue(createMoonChannelUID("zodiac#sign"), moon);
    }

    private ChannelUID createSunChannelUID(String sunChannel) {

        String bindingId = AstroBindingConstants.BINDING_ID;
        String sun = AstroBindingConstants.SUN;
        String thingId = "8c2f8c23";

        return new ChannelUID(bindingId + AbstractUID.SEPARATOR + sun + AbstractUID.SEPARATOR + thingId
                + AbstractUID.SEPARATOR + sunChannel);
    }

    private ChannelUID createMoonChannelUID(String moonChannel) {

        String bindingId = AstroBindingConstants.BINDING_ID;
        String sun = AstroBindingConstants.MOON;
        String thingId = "8c2f8c23";

        return new ChannelUID(bindingId + AbstractUID.SEPARATOR + sun + AbstractUID.SEPARATOR + thingId
                + AbstractUID.SEPARATOR + moonChannel);
    }
}
