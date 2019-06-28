package org.openhab.binding.astro.internal.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

/***
 * Some basic tests of DateTimeUtils.
 * 
 * @author wmarkowski
 * @see <a href="https://www.epochconverter.com">Epoch Converter - Unix
 *      Timestamp Converter</a>
 * @see <a href="http://www.onlineconversion.com/julian_date.htm">Online
 *      Conversion - Julian Date Converter</a>
 */
public class DateTimeUtilsTest {

    @Test
    public void testDateToJulianDateFor1970EpochInUTC() {
        Calendar epoch = new GregorianCalendar(1970, Calendar.JANUARY, 1, 0, 0, 0);
        epoch.set(Calendar.MILLISECOND, 0);
        epoch.setTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals(0, epoch.getTimeInMillis());

        double epochJD = DateTimeUtils.dateToJulianDate(epoch);

        assertEquals(2440587.5, epochJD, 0.00001);
    }

    @Test
    public void testToCalendarFor1970Epoch() {
        Calendar epoch = DateTimeUtils.toCalendar(2440587.5);

        assertEquals(0, epoch.getTimeInMillis());
        // TODO: consider if the created Calendar should be in UTC time zone
        // assertEquals(TimeZone.getTimeZone("UTC"), epoch.getTimeZone());
    }

    @Test
    public void testDateToJulianDateFor2000EpochInUTC() {
        Calendar epoch = new GregorianCalendar(2000, Calendar.JANUARY, 1, 12, 0, 0);
        epoch.set(Calendar.MILLISECOND, 0);
        epoch.setTimeZone(TimeZone.getTimeZone("UTC"));

        double epochJD = DateTimeUtils.dateToJulianDate(epoch);

        assertEquals(2451545.0, epochJD, 0.00001);
    }

    @Test
    public void testToCalendarFor2000Epoch() {
        Calendar epoch = DateTimeUtils.toCalendar(2451545.0);

        assertEquals(946728000000L, epoch.getTimeInMillis());
        // TODO: consider if the created Calendar should be in UTC time zone
        // assertEquals(TimeZone.getTimeZone("UTC"), epoch.getTimeZone());
    }

    @Test
    public void testDateToJulianDateForDateNotInUTC() {
        Calendar epoch = new GregorianCalendar(2019, Calendar.JUNE, 28, 9, 35, 56);
        epoch.set(Calendar.MILLISECOND, 0);
        epoch.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));

        double epochJD = DateTimeUtils.dateToJulianDate(epoch);

        assertEquals(2458662.81662, epochJD, 0.00001);
    }

    @Test
    public void testTruncateToMidnightForCalendarWithUTC() {
        Calendar calendar = new GregorianCalendar(2019, Calendar.JUNE, 28, 9, 35, 56);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar truncated = DateTimeUtils.truncateToMidnight(calendar);
        assertEquals(1561680000000L, truncated.getTimeInMillis());
        assertEquals(TimeZone.getTimeZone("UTC"), truncated.getTimeZone());
    }

    @Test
    public void testTruncateToMidnightForCalendarWithOtherZone() {
        Calendar calendar = new GregorianCalendar(2019, Calendar.JUNE, 28, 9, 35, 56);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));

        Calendar truncated = DateTimeUtils.truncateToMidnight(calendar);

        assertEquals(1561672800000L, truncated.getTimeInMillis());
        assertEquals(TimeZone.getTimeZone("Europe/Warsaw"), truncated.getTimeZone());
        assertEquals(28, truncated.get(Calendar.DAY_OF_MONTH));
        assertEquals(0, truncated.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, truncated.get(Calendar.MINUTE));
        assertEquals(0, truncated.get(Calendar.SECOND));
        assertEquals(0, truncated.get(Calendar.MILLISECOND));

        // but change the time zone to UTC
        truncated.setTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals(1561672800000L, truncated.getTimeInMillis());
        assertEquals(TimeZone.getTimeZone("UTC"), truncated.getTimeZone());
        assertEquals(27, truncated.get(Calendar.DAY_OF_MONTH));
        assertEquals(22, truncated.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, truncated.get(Calendar.MINUTE));
        assertEquals(0, truncated.get(Calendar.SECOND));
        assertEquals(0, truncated.get(Calendar.MILLISECOND));
    }

    @Test
    public void testMidnightDateToJulianDateForNonUTCDate() {
        Calendar calendar = new GregorianCalendar(2019, Calendar.JUNE, 28, 9, 35, 56);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));

        double jd = DateTimeUtils.midnightDateToJulianDate(calendar);

        assertEquals(2458662.41667, jd, 0.00001);
    }
}
