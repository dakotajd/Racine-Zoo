package edu.uwp.appfactory.racinezoo;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.uwp.appfactory.racinezoo.Util.DateUtils;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void testBuildDate() {
        int dayOfMonth = 6;
        Date date = DateUtils.buildDate(6);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int testDay = c.get(Calendar.DAY_OF_MONTH);
        assertTrue(testDay == dayOfMonth);
        Date now = new Date(System.currentTimeMillis());
        Calendar c1 = new GregorianCalendar();
        c1.setTime(now);
        assertTrue(c1.get(Calendar.YEAR) == c.get(Calendar.YEAR));
        assertTrue(c1.get(Calendar.MONTH) == c.get(Calendar.MONTH));
    }

    @Test
    public void testFormatAsDayOfWeek() {
        Date date = new Date();
        String s = DateUtils.formatDateAsDayOfWeek(date);
        assertTrue(s.toUpperCase().matches("[A-Z]{3}$"));
    }

    @Test
    public void testFormatAsDayOfMonth() {
        Date date = new Date();
        String s = DateUtils.formatDateAsDayOfMonth(date);
        assertTrue(s.toUpperCase().matches("[0-9]{1,2}\\s[A-Z]{3}$"));
    }

    @Test
    public void testGetWeekInYearFromDate() {
        Date date = new Date();
        int testWeekInYearFromDate = DateUtils.getWeekInYearFromDate(date);
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int weekInYear = c.get(Calendar.WEEK_OF_YEAR);
        assertTrue(testWeekInYearFromDate == weekInYear);
    }

    @Test
    public void testGetCurrentWeekInYear() {
        Date now = new Date(System.currentTimeMillis());
        Calendar c = new GregorianCalendar();
        c.setTime(now);
        int weekInYearTest = DateUtils.getCurrentWeekInYear();
        int weekInYear = c.get(Calendar.WEEK_OF_YEAR);
        assertTrue(weekInYearTest == weekInYear);
    }

    @Test
    public void testGetDayRangeInWeek() {
        String s = DateUtils.getDayRangeInWeek(1);
        assertTrue(s.toUpperCase().matches("[A-Z]{3}\\s[0-9]{1,2}\\s-\\s[A-Z]{3}\\s[0-9]{1,2}"));
    }

    @Test
    public void testFormatDateAsTime() {
        String s = DateUtils.formatDateAsTime(new Date());
        assertTrue(s, s.matches("(\\d{1,2}:\\d\\d)(A|P)M"));
    }

    @Test
    public void testGetDayOfMonthFromDate() {
        Date date = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        assertTrue(dayOfMonth == DateUtils.getDayOfMonthFromDate(date));
    }
}
