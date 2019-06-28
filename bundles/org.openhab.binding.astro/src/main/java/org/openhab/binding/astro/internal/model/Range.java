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

import static org.eclipse.smarthome.core.library.unit.MetricPrefix.MILLI;

import java.util.Calendar;

import javax.measure.quantity.Time;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.openhab.binding.astro.internal.util.DateTimeUtils;

/**
 * Range class which holds a start and a end calendar object.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 */
public class Range {

    private Calendar start;
    private Calendar end;

    public Range() {
    }

    public Range(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start of the range.
     */
    public Calendar getStart() {
        return start;
    }

    /**
     * Returns the end of the range.
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * Returns the duration in minutes.
     */
    public QuantityType<Time> getDuration() {
        if (start == null || end == null) {
            return null;
        }
        if (start.after(end)) {
            return new QuantityType<Time>(0, SmartHomeUnits.MINUTE);
        }
        return new QuantityType<Time>(end.getTimeInMillis() - start.getTimeInMillis(), MILLI(SmartHomeUnits.SECOND))
                .toUnit(SmartHomeUnits.MINUTE);
    }

    /***
     * Checks if the range is bounded, which is only when {@link #getStart()} and
     * {@link #getEnd()} are both not null.
     *
     */
    public boolean isBounded() {
        if (start == null) {
            return false;
        }
        if (end == null) {
            return false;
        }

        return true;
    }

    /***
     * Checks if current range intersect with the other one.
     */
    public boolean hasIntersection(Range other) {
        if(end == null && start == null)
        {
            return false;
        }
        
        if (end != null && other.start != null && other.start.getTimeInMillis() > end.getTimeInMillis()) {
            return false;
        }

        if (start != null && other.end != null && other.end.getTimeInMillis() < start.getTimeInMillis()) {
            return false;
        }

        return true;
    }

    /**
     * Returns true, if the given calendar matches into the range.
     */
    public boolean matches(Calendar cal) {
        if (start == null && end == null) {
            return false;
        }
        long matchStart = start != null ? start.getTimeInMillis()
                : DateTimeUtils.truncateToMidnight(cal).getTimeInMillis();
        long matchEnd = end != null ? end.getTimeInMillis() : DateTimeUtils.endOfDayDate(cal).getTimeInMillis();
        return cal.getTimeInMillis() >= matchStart && cal.getTimeInMillis() < matchEnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("start", DateTimeUtils.getDate(start))
                .append("end", DateTimeUtils.getDate(end)).toString();
    }
}
