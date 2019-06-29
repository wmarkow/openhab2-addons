package org.openhab.binding.astro.internal.calc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.astro.TestUtils;
import org.openhab.binding.astro.internal.model.Sun;

/***
 * Tests the sun daily events for Alert location in Canada. In particular it
 * checks the behavior of night in December.
 * 
 * @author Witold Markowski
 *
 */
public class SunCalcAlertTest extends AbstractSunCalcLocationTest {

    private final static double ALERT_LATITUDE = 82.5059388;
    private final static double ALERT_LONGITUDE = -62.5296812;
    private final static TimeZone ALERT_TIME_ZONE = TimeZone.getTimeZone("EST");

    private SunCalc subject;

    @Before
    public void init() {
        subject = new SunCalc();
    }

    @Test
    public void testRangesFor_2019_Dec_21_at_midnight() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 0, 0, ALERT_TIME_ZONE);
        TimeZone.getAvailableIDs();
        Sun sun = subject.getSunInfo(date, ALERT_LATITUDE, ALERT_LONGITUDE, 0.0);

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        assertNull(sun.getDaylight().getStart());
        assertNull(sun.getDaylight().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 8, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getSet().getStart());
        assertNull(sun.getSet().getEnd());

        assertNull(sun.getCivilDusk().getStart());
        assertNull(sun.getCivilDusk().getEnd());

        assertNull(sun.getNauticDusk().getStart());
        assertNull(sun.getNauticDusk().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 23, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show current night from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 14, 4, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // TRUE MIDNIGHT is on 20th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Dec_21_at_midnight() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.DECEMBER, 21, 0, 0), ALERT_LATITUDE, ALERT_LONGITUDE);
    }

    @Test
    public void testRangesFor_2019_Dec_21_in_astroDusk() {
        Calendar date = TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 12, 0, ALERT_TIME_ZONE);
        TimeZone.getAvailableIDs();
        Sun sun = subject.getSunInfo(date, ALERT_LATITUDE, ALERT_LONGITUDE, 0.0);

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDawn().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getNauticDawn().getStart());
        assertNull(sun.getNauticDawn().getEnd());

        assertNull(sun.getCivilDawn().getStart());
        assertNull(sun.getCivilDawn().getEnd());

        assertNull(sun.getRise().getStart());
        assertNull(sun.getRise().getEnd());

        assertNull(sun.getDaylight().getStart());
        assertNull(sun.getDaylight().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 8, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNoon().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        assertNull(sun.getSet().getStart());
        assertNull(sun.getSet().getEnd());

        assertNull(sun.getCivilDusk().getStart());
        assertNull(sun.getCivilDusk().getEnd());

        assertNull(sun.getNauticDusk().getStart());
        assertNull(sun.getNauticDusk().getEnd());

        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 11, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getAstroDusk().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show morning night from 20th/21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 8, 9, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getMorningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show evening night from 21th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 23, 7, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getEveningNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // show current night from 21th/22th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 21, 14, 4, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNight().getStart().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 22, 8, 9, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getNight().getEnd().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);

        // TRUE MIDNIGHT is on 20th
        assertEquals(TestUtils.newCalendar(2019, Calendar.DECEMBER, 20, 23, 6, ALERT_TIME_ZONE).getTimeInMillis(),
                sun.getTrueMidnight().getTimeInMillis(), NOON_ACCURACY_IN_MILLIS);
    }

    @Test
    public void testRangesCoherenceFor_2019_Dec_21_in_astroDusk() {
        testRangesCoherence(new GregorianCalendar(2019, Calendar.DECEMBER, 21, 12, 0), ALERT_LATITUDE, ALERT_LONGITUDE);
    }
}
