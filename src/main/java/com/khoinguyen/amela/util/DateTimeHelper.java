package com.khoinguyen.amela.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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

    public static LocalDateTime toDateTime(long unix) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(unix), TimeZone.getDefault().toZoneId());
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
}
