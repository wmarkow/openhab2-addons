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

/***
 * Abstract class for Sun specific calculations. All formulas provided by this
 * class were translated to java based on the javascript calculations of
 * http://www.suncalc.net
 * 
 * @author Witold Markowski
 * @see <a href="http://www.suncalc.net">SunCalc - sun position, sunlight
 *      phases, sunrise, sunset, dusk and dawn times calculator</a>
 */
abstract class AbstractSunCalc {

    protected static final double J2000 = 2451545.0;
    protected static final double DEG2RAD = Math.PI / 180;

    protected static final double SUN_DIAMETER = 0.53 * DEG2RAD; // sun diameter
    protected static final double SUN_ANGLE = -0.83;

    private static final double M0 = 357.5291 * DEG2RAD;
    private static final double M1 = 0.98560028 * DEG2RAD;
    private static final double J0 = 0.0009;
    private static final double J1 = 0.0053;
    private static final double J2 = -0.0069;
    private static final double C1 = 1.9148 * DEG2RAD;
    private static final double C2 = 0.0200 * DEG2RAD;
    private static final double C3 = 0.0003 * DEG2RAD;
    private static final double P = 102.9372 * DEG2RAD;
    private static final double E = 23.45 * DEG2RAD;
    private static final double TH0 = 280.1600 * DEG2RAD;
    private static final double TH1 = 360.9856235 * DEG2RAD;

    protected double getJulianCycle(double j, double lw) {
        return Math.round(j - J2000 - J0 - lw / (2 * Math.PI));
    }

    protected double getApproxSolarTransit(double ht, double lw, double n) {
        return J2000 + J0 + (ht + lw) / (2 * Math.PI) + n;
    }

    protected double getSolarMeanAnomaly(double js) {
        return M0 + M1 * (js - J2000);
    }

    protected double getEquationOfCenter(double m) {
        return C1 * Math.sin(m) + C2 * Math.sin(2 * m) + C3 * Math.sin(3 * m);
    }

    protected double getEclipticLongitude(double m, double c) {
        return m + P + c + Math.PI;
    }

    protected double getSolarTransit(double js, double m, double lsun) {
        return js + (J1 * Math.sin(m)) + (J2 * Math.sin(2 * lsun));
    }

    protected double getSunDeclination(double lsun) {
        return Math.asin(Math.sin(lsun) * Math.sin(E));
    }

    protected double getRightAscension(double lsun) {
        return Math.atan2(Math.sin(lsun) * Math.cos(E), Math.cos(lsun));
    }

    protected double getSiderealTime(double j, double lw) {
        return TH0 + TH1 * (j - J2000) - lw;
    }

    protected double getAzimuth(double th, double a, double phi, double d) {
        double h = th - a;
        return Math.atan2(Math.sin(h), Math.cos(h) * Math.sin(phi) - Math.tan(d) * Math.cos(phi));
    }

    protected double getElevation(double th, double a, double phi, double d) {
        return Math.asin(Math.sin(phi) * Math.sin(d) + Math.cos(phi) * Math.cos(d) * Math.cos(th - a));
    }

    protected double getShadeLength(double elevation) {
        return 1 / Math.tan(elevation * DEG2RAD);
    }

    protected double getHourAngle(double h, double phi, double d) {
        return Math.acos((Math.sin(h) - Math.sin(phi) * Math.sin(d)) / (Math.cos(phi) * Math.cos(d)));
    }

    protected double getSunsetJulianDate(double w0, double m, double Lsun, double lw, double n) {
        return getSolarTransit(getApproxSolarTransit(w0, lw, n), m, Lsun);
    }

    protected double getSunriseJulianDate(double jtransit, double jset) {
        return jtransit - (jset - jtransit);
    }
}
