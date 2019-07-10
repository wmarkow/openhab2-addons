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

import java.util.Calendar;

import org.openhab.binding.astro.internal.model.Position;
import org.openhab.binding.astro.internal.model.Radiation;

/***
 * For a specific date and sun elevation calculates {@link Radiation}.
 * 
 * @author Witold Markowski - Refactoring
 * @see based on the calculations of http://www.suncalc.net
 * 
 */
public class SunRadiationCalc {
    private static final double SC = 1367; // Solar constant in W/m²

    public Radiation calculate(Calendar calendar, double sunElevation, Double altitude) {
        double sinAlpha = Math.sin(Math.toRadians(sunElevation));

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        // Direct Solar Radiation (in W/m²) at the atmosphere entry
        // At sunrise/sunset - calculations limits are reached
        double rOut = (sunElevation > 3) ? SC * (0.034 * Math.cos(Math.toRadians(360 * dayOfYear / daysInYear)) + 1)
                : 0;
        double altitudeRatio = (altitude != null) ? 1 / Math.pow((1 - (6.5 / 288) * (altitude / 1000.0)), 5.256) : 1;
        double m = (Math.sqrt(1229 + Math.pow(614 * sinAlpha, 2)) - 614 * sinAlpha) * altitudeRatio;

        // Direct radiation after atmospheric layer
        // 0.6 = Coefficient de transmissivité
        double rDir = rOut * Math.pow(0.6, m) * sinAlpha;

        // Diffuse Radiation
        double rDiff = rOut * (0.271 - 0.294 * Math.pow(0.6, m)) * sinAlpha;
        double rTot = rDir + rDiff;

        Radiation radiation = new Radiation();
        radiation.setDirect(rDir);
        radiation.setDiffuse(rDiff);
        radiation.setTotal(rTot);

        return radiation;
    }
}
