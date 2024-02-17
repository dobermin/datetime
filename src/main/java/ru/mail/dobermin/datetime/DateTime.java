package ru.mail.dobermin.datetime;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.TimeZone;

public class DateTime {

    private static Calendar calendar;
    private String day;
    private String month;
    private String hour;
    private String minute;

    public DateTime (int day, int month, int year, int hour, int minute) {
        getCalendar();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
    }

    public DateTime (String day, String month, String year, String hour, String minute) {
        new DateTime(
                Integer.parseInt(day),
                getMonth(month),
                Integer.parseInt(year),
                Integer.parseInt(hour),
                Integer.parseInt(minute)
        );
    }

    public DateTime () {
        getCalendar();
    }

    public DateTime (String hour, String minute) {
        getCalendar();
        calendar.set(Calendar.HOUR, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
    }
    public DateTime (String day, String month, String hour, String minute) {
        getCalendar();
        calendar.set(Calendar.DATE, Integer.parseInt(day));
        calendar.set(Calendar.MONTH, getMonth(month));
        calendar.set(Calendar.HOUR, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
    }

    public Long getLong () {
        return calendar.getTimeInMillis();
    }

    public Long getYesterday () {
        return getLong() - 24 * 3600000;
    }

    public static int getMonth (String month) {
        switch (month.substring(0,3)) {
            case "фев": return 1;
            case "мар": return 2;
            case "апр": return 3;
            case "мая":
            case "май": return 4;
            case "июн": return 5;
            case "июл": return 6;
            case "авг": return 7;
            case "сен": return 8;
            case "окт": return 9;
            case "ноя": return 10;
            case "дек": return 11;
            default: return 0;
        }
    }

    private static void getCalendar() {
        calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow"));
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public void setDay (String day) {
        calendar.set(Calendar.DATE, Integer.parseInt(day));
    }

    public void setMonth (String month) {
        calendar.set(Calendar.MONTH, getMonth(month));
    }

    public void setHour (String hour) {
        calendar.set(Calendar.HOUR, Integer.parseInt(hour));
    }

    public void setMinute (String minute) {
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
    }

    //https://stackoverflow.com/questions/18915075/java-convert-string-to-timestamp
    public static Timestamp convertStringToTimestamp(String strDate, String pattern) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return Optional.ofNullable(strDate)
                .map(str -> LocalDateTime.parse(str, formatter))
                .map(Timestamp::valueOf)
                .orElse(null);
    }

    public static Timestamp convertStringToTimestamp(String strTime) {
        return Optional.ofNullable(strTime)
                .map(str -> new Timestamp(new DateTime(str.substring(0, 2), str.substring(3)).getLong()))
                .orElse(null);
    }
}
