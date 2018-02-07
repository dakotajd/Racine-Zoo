package edu.uwp.appfactory.racinezoo.Util;

/**
 * Utility file I pulled in order to handle correct processing of dates.
 *
 * Check later for scope 2/22 (public)
 */


import android.support.annotation.NonNull;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static edu.uwp.appfactory.racinezoo.Util.Config.BEACON_WAIT_INTERVAL;

public class DateUtils {

    public static Date buildDate(@IntRange(from = 1, to = 31) int dayOfMonth) {
        return buildDate(dayOfMonth, getCurrentMonth());
    }

    private static Date buildDate(
            @IntRange(from = 1, to = 31) int dayOfMonth,
            @IntRange(from = 0, to = 11) int month) {
        return buildDate(dayOfMonth, month, getCurrentYear());
    }

    private static Date buildDate(
            @IntRange(from = 1, to = 31) int dayOfMonth,
            @IntRange(from = 0, to = 11) int month,
            @IntRange(from = 0) int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formatDate(@NonNull Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return df.format(date);
    }

    @IntRange(from = 0, to = 11)
    private static int getCurrentMonth() {
        Date now = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        return calendar.get(Calendar.MONTH);
    }

    @IntRange(from = 0)
    public static int getCurrentYear() {
        Date now = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        return calendar.get(Calendar.YEAR);
    }

    public static String formatDateAsDayOfWeek(@NonNull Date date) {
        SimpleDateFormat df = new SimpleDateFormat("EEE", Locale.getDefault());
        return df.format(date);
    }

    public static String formatDateAsDayOfMonth(@NonNull Date date) {
        SimpleDateFormat df = new SimpleDateFormat("d MMM", Locale.getDefault());
        return df.format(date);
    }

    public static int getWeekInYearFromDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentWeekInYear() {
        Date now = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static String getDayRangeInWeek(int week) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date firstDay = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date secondDay = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM d", Locale.getDefault());
        return df.format(firstDay) + " - " + df.format(secondDay);
    }

    public static int getDayOfMonthFromDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatDateAsTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("h:mma", Locale.getDefault());
        return df.format(date);
    }

    public static String getMonthNameFromDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MMMM", Locale.getDefault());
        return df.format(date);
    }

    public static int getMonthNumberFromDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static String getDateForNotification(Date startDate, Date endDate) {
        SimpleDateFormat time = new SimpleDateFormat("h:mma", Locale.getDefault());
        SimpleDateFormat day = new SimpleDateFormat("EEE", Locale.getDefault());
        return day.format(startDate) + ", " + time.format(startDate) + " to " + time.format(endDate);
    }

    public static String formatDatesAsSubtitle(Date start, Date end) {
        SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d,", Locale.getDefault());
        String subtitle = df.format(start);
        df = new SimpleDateFormat("h:mma", Locale.getDefault());
        subtitle = subtitle + " " + df.format(start) + " - " + df.format(end);
        return subtitle;
    }

    public static boolean checkAnimalTime(long lastSeen){
        Log.d("MEH","last seen:" + lastSeen);
        if(lastSeen>0){
            long currentTime = new Date().getTime();
            long interval = currentTime - lastSeen;

            Log.d("MEH","interval:" + interval);

            //1 hour
            if(interval< BEACON_WAIT_INTERVAL){
                return false;
            }
        }
        return true;
    }
}
