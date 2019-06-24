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

import org.openhab.binding.astro.internal.model.SunDailyEvents;
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/***
 * For a specific date, time and geolocation calculates {@link SunDailyEvents}.
 * 
 * @author Witold Markowski
 *
 */
class SunDailyEventsCalc extends AbstractSunCalc {

    private static final double H0 = SUN_ANGLE * DEG2RAD;
    private static final double H1 = -6.0 * DEG2RAD; // nautical twilight angle
    private static final double H2 = -12.0 * DEG2RAD; // astronomical twilight
                                                      // angle
    private static final double H3 = -18.0 * DEG2RAD; // darkness angle

    public SunDailyEvents calculate(Calendar calendar, double latitude, double longitude, Double altitude) {
        double lw = -longitude * DEG2RAD;
        double phi = latitude * DEG2RAD;
        double j = DateTimeUtils.midnightDateToJulianDate(calendar) + 0.5;
        double n = getJulianCycle(j, lw);
        double js = getApproxSolarTransit(0, lw, n);
        double m = getSolarMeanAnomaly(js);
        double c = getEquationOfCenter(m);
        double lsun = getEclipticLongitude(m, c);
        double d = getSunDeclination(lsun);
        double transit = getSolarTransit(js, m, lsun);
        double w0 = getHourAngle(H0, phi, d);
        double w1 = getHourAngle(H0 + SUN_DIAMETER, phi, d);
        double setEnd = getSunsetJulianDate(w0, m, lsun, lw, n);
        double setStart = getSunsetJulianDate(w1, m, lsun, lw, n);
        double riseStart = getSunriseJulianDate(transit, setEnd);
        double riseEnd = getSunriseJulianDate(transit, setStart);
        double w2 = getHourAngle(H1, phi, d);
        double nauticDuskStart = getSunsetJulianDate(w2, m, lsun, lw, n);
        double civilDawnStart = getSunriseJulianDate(transit, nauticDuskStart);

        double w3 = getHourAngle(H2, phi, d);
        double w4 = getHourAngle(H3, phi, d);
        double astroDuskStart = getSunsetJulianDate(w3, m, lsun, lw, n);
        double nightStart = getSunsetJulianDate(w4, m, lsun, lw, n);
        double nauticDawnStart = getSunriseJulianDate(transit, astroDuskStart);
        double astroDawnStart = getSunriseJulianDate(transit, nightStart);

        SunDailyEvents result = new SunDailyEvents();
        result.transit = transit;
        result.setEnd = setEnd;
        result.setStart = setStart;
        result.riseStart = riseStart;
        result.riseEnd = riseEnd;
        result.nauticDuskStart = nauticDuskStart;
        result.civilDawnStart = civilDawnStart;
        result.astroDuskStart = astroDuskStart;
        result.nightStart = nightStart;
        result.nauticDawnStart = nauticDawnStart;
        result.astroDawnStart = astroDawnStart;

        return result;
    }
}
