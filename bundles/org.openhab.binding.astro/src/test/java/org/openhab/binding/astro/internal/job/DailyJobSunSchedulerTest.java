package org.openhab.binding.astro.internal.job;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.openhab.binding.astro.TestUtils;

public class DailyJobSunSchedulerTest {

    private DailyJobSunScheduler subject;

    @Before
    public void init() {
        subject = new DailyJobSunScheduler();
    }

    @Test
    public void testBorderCaseWhenNightNullAndNoonFarAwayInPast() {

        Calendar now = TestUtils.newCalendar(2019, Calendar.JULY, 17, 16, 23, TimeZone.getDefault());
        Calendar noon = TestUtils.newCalendar(2019, Calendar.JULY, 15, 16, 23, TimeZone.getDefault());

        Calendar result = subject.findNextExecutionTime(now, noon, null);

        // expected result: next midnight from now
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 18, 0, 0, 30, TimeZone.getDefault()).getTimeInMillis(),
                result.getTimeInMillis());
    }

    @Test
    public void testBorderCaseWhenNightNullAndNoonFarAwayInFuture() {

        Calendar now = TestUtils.newCalendar(2019, Calendar.JULY, 17, 16, 23, TimeZone.getDefault());
        Calendar noon = TestUtils.newCalendar(2019, Calendar.JULY, 18, 16, 23, TimeZone.getDefault());

        Calendar result = subject.findNextExecutionTime(now, noon, null);

        // expected result: next midnight from now
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 18, 0, 0, 30, TimeZone.getDefault()).getTimeInMillis(),
                result.getTimeInMillis());
    }
    
    @Test
    public void testWhenNightNullAndNowAfterTrueMidnightButBeforeMidnight() {

        Calendar now = TestUtils.newCalendar(2019, Calendar.JULY, 17, 16, 23, TimeZone.getDefault());
        Calendar noon = TestUtils.newCalendar(2019, Calendar.JULY, 17, 12, 33, TimeZone.getDefault());

        Calendar result = subject.findNextExecutionTime(now, noon, null);

        // expected result: next midnight from now
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 18, 0, 0, 30, TimeZone.getDefault()).getTimeInMillis(),
                result.getTimeInMillis());
    }
    
    @Test
    public void testWhenNightNullAndNowAfterMidnightButBeforeTrueMidnight() {

        Calendar now = TestUtils.newCalendar(2019, Calendar.JULY, 17, 0, 23, TimeZone.getDefault());
        Calendar noon = TestUtils.newCalendar(2019, Calendar.JULY, 17, 12, 33, TimeZone.getDefault());

        Calendar result = subject.findNextExecutionTime(now, noon, null);

        // expected result: next true midnight from now
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 17, 0, 33, 30, TimeZone.getDefault()).getTimeInMillis(),
                result.getTimeInMillis());
    }
    
    @Test
    public void testWhenNowAfterTrueMidnightButBeforeNightEnd() {

        Calendar now = TestUtils.newCalendar(2019, Calendar.JULY, 17, 1, 23, TimeZone.getDefault());
        Calendar morningNightEnd = TestUtils.newCalendar(2019, Calendar.JULY, 17, 3, 54, TimeZone.getDefault());
        Calendar noon = TestUtils.newCalendar(2019, Calendar.JULY, 17, 12, 33, TimeZone.getDefault());

        Calendar result = subject.findNextExecutionTime(now, noon, morningNightEnd);

        // expected result: next true midnight from now
        assertEquals(TestUtils.newCalendar(2019, Calendar.JULY, 17, 3, 54, 30, TimeZone.getDefault()).getTimeInMillis(),
                result.getTimeInMillis());
    }
}
