package org.openhab.binding.astro;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TestUtils {
    public static Calendar newCalendar(int year, int month, int day, int hour, int minute, TimeZone timeZone) {
        Calendar result = new GregorianCalendar(year, month, day, hour, minute);
        result.setTimeZone(timeZone);

        return result;
    }

    public static Calendar newCalendar(int year, int month, int day, int hour, int minute, int second,
            TimeZone timeZone) {
        Calendar result = new GregorianCalendar(year, month, day, hour, minute, second);
        result.setTimeZone(timeZone);

        return result;
    }
}
