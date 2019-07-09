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
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/***
 * For a specific date, time and geolocation calculates {@link Position}.
 * 
 * @author Witold Markowski
 *
 */
public class SunPositionCalc extends AbstractSunCalc {

    public Position calculate(Calendar calendar, double latitude, double longitude) {
        double lw = -longitude * DEG2RAD;
        double phi = latitude * DEG2RAD;

        double j = DateTimeUtils.dateToJulianDate(calendar);
        double m = getSolarMeanAnomaly(j);
        double c = getEquationOfCenter(m);
        double lsun = getEclipticLongitude(m, c);
        double d = getSunDeclination(lsun);
        double a = getRightAscension(lsun);
        double th = getSiderealTime(j, lw);

        double azimuth = getAzimuth(th, a, phi, d) / DEG2RAD;
        double elevation = getElevation(th, a, phi, d) / DEG2RAD;
        double shadeLength = getShadeLength(elevation);

        Position position = new Position();
        position.setAzimuth(azimuth + 180);
        position.setElevation(elevation);
        position.setShadeLength(shadeLength);

        return position;
    }
}
