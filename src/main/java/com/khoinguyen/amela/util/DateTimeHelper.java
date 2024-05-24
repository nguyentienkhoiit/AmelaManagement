package com.khoinguyen.amela.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateTimeHelper {
    public static boolean isExpiredDay(LocalDate day, int number) {
        LocalDate today = LocalDate.now();
        long daysDifference = ChronoUnit.DAYS.between(day, today);
        return daysDifference >= 0 && daysDifference < number;
    }

    public static String getDateFromMinus(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    public static int getMinus(String time) {
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        return hours * 60 + minutes;
    }

    public static String toDayOfWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return switch (dayOfWeek) {
            case SATURDAY -> "Thứ Bảy";
            case SUNDAY -> "Chủ Nhật";
            case MONDAY -> "Thứ Hai";
            case TUESDAY -> "Thứ Ba";
            case WEDNESDAY -> "Thứ Tư";
            case THURSDAY -> "Thứ Năm";
            case FRIDAY -> "Thứ sáu";
        };
    }

    public static boolean compareDateGreaterThan(LocalDate date, Long age) {
        if (date == null) return false;
        LocalDate now = LocalDate.now();
        Period period = Period.between(date, now);
        return period.getYears() <= age;
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String formatLocalTime(LocalTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String formatLocalDateTimeFullText(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    public static LocalDate parseStringToDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDateTime getExpirationTime() {
        return getTimeNow().plusSeconds(Constant.EMAIL_WAITING_EXPIRATION);
    }

    public static LocalDateTime getTimeNow() {
        ZonedDateTime nowInVietnam = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        return nowInVietnam.toLocalDateTime();
    }

    public static long toUnixTime(String dateTime, String format) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
        return localDateTime.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() * 1000;
    }

    public static long getCurrentUnixTime() {
        return Instant.now().toEpochMilli();
    }

    public static long getCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return localDateTime.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() * 1000;
    }

    public static LocalDateTime toDateTime(long unix) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unix), TimeZone.getDefault().toZoneId());
    }

    public static Date toDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static long toMilliSecond(String timeStr) {
        LocalTime localTime = LocalTime.parse(timeStr);
        return (localTime.getHour() * 60 * 60 + localTime.getMinute() * 60 + localTime.getSecond()) * 1000;
    }

    public static String formatTime(long milliSeconds, String format) {
        long second = milliSeconds / 1000;
        int hh = (int) (second / 3600);
        int mm = (int) ((second % 3600) / 60);
        int ss = (int) ((second % 3600) % 60);

        LocalTime localTime = LocalTime.of(hh, mm, ss);
        return DateTimeFormatter.ofPattern(format).format(localTime);
    }

    public static String formatTime(long second) {
        return formatTime(second, "HH:mm");
    }

    public static String formatDate(long unix, String format) {
        LocalDateTime localDateTime = toDateTime(unix);
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

    public static String formatDate(long unix) {
        return formatDate(unix, "yyyy-MM-dd");
    }

    public static Map<String, Integer> getYearMonthDetail(String dateStr) {
        if (dateStr == null) return null;

        Map<String, Integer> map = new HashMap<>();
        try {
            LocalDate date = LocalDate.parse(dateStr + "-01");
            int year = date.getYear();
            int month = date.getMonthValue();

            map.put("year", year);
            map.put("month", month);

            return map;
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isExpiredDay(LocalDate l1, LocalDate l2, int numberOfDays) {
        if (l1 == null || l2 == null) return false;
        Duration duration = Duration.between(l1.atStartOfDay(), l2.atStartOfDay());

        long absDays = Math.abs(duration.toDays());
        long numberOfDay = Duration.ofDays(numberOfDays).toDays();
        return absDays >= numberOfDay;
    }
}
