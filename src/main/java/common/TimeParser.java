package common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser that can parse a string into a {@link Temporal}.
 */
public class TimeParser {
    private static final Pattern YEAR = Pattern.compile("^.*(\\d{4}).*$");
    private static final Pattern MM_DD_DASH = Pattern.compile("^.*(1[0-2]|0?[1-9])-([12]\\d|3[01]|0?[1-9]).*$");
    private static final Pattern DD_MM_SLASH = Pattern.compile("^.*([12]\\d|3[01]|0?[1-9])/(1[0-2]|0?[1-9]).*$");
    private static final Pattern MM_DD_DOT = Pattern.compile("^.*(1[0-2]|0?[1-9])\\.([12]\\d|3[01]|0?[1-9]).*$");
    private static final Pattern MMM_DD = Pattern.compile("^.*([A-Za-z]{3,9})\\s+([12]\\d|3[01]|0?[1-9]).*$");
    private static final Pattern DAYS_OF_WEEK =
            Pattern.compile("^.*(mon|monday|tue|tues|tuesday|wed|wednesday|thu" + "|thur|thurs|thursday|fri|friday"
                    + "|sat|saturday|sun" + "|sunday).*$");
    private static final Pattern TIME_NOON = Pattern.compile("^.*noon.*$");
    private static final Pattern TIME_MIDNIGHT = Pattern.compile("^.*midnight.*$");
    private static final Pattern TIME_24_HH_COLON_MM = Pattern.compile("^.*\\s*([0-1]\\d:[0-5]\\d|2[0-3]:[0-5]\\d).*$");
    private static final Pattern TIME_12_HH_COLON_MM_AP = Pattern.compile("^.*\\s*((?:1[0-2]|0?[1-9]):[0-5]\\d)"
            + "(am|pm|AM|PM).*$");

    private TimeParser() { }

    /**
     * Formats a {@link Temporal} into a string.
     *
     * @param temporal the temporal to format
     * @param dateTimeFormat the format to use for {@link LocalDateTime}
     * @param dateFormat the format to use for {@link LocalDate}
     * @return the formatted string
     */
    public static String format(Temporal temporal, DateTimeFormatter dateTimeFormat, DateTimeFormatter dateFormat) {
        String res = temporal.toString();
        if (temporal instanceof LocalDateTime dt) {
            res = dt.toLocalDate().equals(LocalDate.now())
                    ? "today " + dt.toLocalTime()
                    : dt.format(dateTimeFormat);
        } else if (temporal instanceof LocalDate d) {
            res = d.equals(LocalDate.now()) ? "today" : d.format(dateFormat);
        }

        return res;
    }

    /**
     * Parses a string into a {@link Temporal}.
     *
     * @param input the string to parse
     * @return the parsed {@link Temporal}
     * @throws IllegalArgumentException if the string is null or blank
     */
    public static Temporal parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("time cannot be null");
        }

        input = input.trim().toLowerCase(Locale.ENGLISH);
        if (input.isBlank()) {
            throw new IllegalArgumentException("time cannot be blank");
        }

        if (input.equals("today")) {
            return LocalDate.now();
        }

        LocalDate date;
        LocalTime time = TimeParser.parseTime(input);
        if (input.startsWith("today ")) {
            date = LocalDate.now();
        } else if (input.equals("tomorrow")) {
            date = LocalDate.now().plusDays(1);
        } else if (input.startsWith("tomorrow ")) {
            date = LocalDate.now().plusDays(1);
        } else {
            DayOfWeek day = TimeParser.parseDayOfWeek(input);
            if (day != null) {
                date = LocalDate.now().with(TemporalAdjusters.next(day));
            } else {
                date = TimeParser.parseDate(input);
            }
        }

        return time == null ? date : LocalDateTime.of(date, time);
    }

    private static LocalDate parseDate(String input) {
        Matcher matcher = YEAR.matcher(input);
        int year = matcher.matches() ? Integer.parseInt(matcher.group(1)) : LocalDate.now().getYear();
        int month;
        int day;

        matcher = MM_DD_DASH.matcher(input);
        if (matcher.matches()) {
            month = Integer.parseInt(matcher.group(1));
            day = Integer.parseInt(matcher.group(2));
            return LocalDate.of(year, month, day);
        }

        matcher = DD_MM_SLASH.matcher(input);
        if (matcher.matches()) {
            day = Integer.parseInt(matcher.group(1));
            month = Integer.parseInt(matcher.group(2));
            return LocalDate.of(year, month, day);
        }

        matcher = MM_DD_DOT.matcher(input);
        if (matcher.matches()) {
            month = Integer.parseInt(matcher.group(1));
            day = Integer.parseInt(matcher.group(2));
            return LocalDate.of(year, month, day);
        }

        matcher = MMM_DD.matcher(input);
        if (matcher.matches()) {
            month = TimeParser.monthFromName(matcher.group(1).toLowerCase(Locale.ENGLISH));
            day = Integer.parseInt(matcher.group(2));
            return LocalDate.of(year, month, day);
        }

        return LocalDate.now();
    }

    private static int monthFromName(String month) {
        return switch (month) {
        case "jan", "january" -> 1;
        case "feb", "february" -> 2;
        case "mar", "march" -> 3;
        case "apr", "april" -> 4;
        case "may" -> 5;
        case "jun", "june" -> 6;
        case "jul", "july" -> 7;
        case "aug", "august" -> 8;
        case "sep", "sept", "september" -> 9;
        case "oct", "october" -> 10;
        case "nov", "november" -> 11;
        case "dec", "december" -> 12;
        default -> throw new IllegalArgumentException("Unknown month: " + month);
        };
    }

    private static DayOfWeek parseDayOfWeek(String input) {
        Matcher matcher = DAYS_OF_WEEK.matcher(input);
        if (!matcher.matches()) {
            return null;
        }

        return switch (matcher.group(1)) {
        case "mon", "monday" -> DayOfWeek.MONDAY;
        case "tue", "tues", "tuesday" -> DayOfWeek.TUESDAY;
        case "wed", "weds", "wednesday" -> DayOfWeek.WEDNESDAY;
        case "thu", "thur", "thurs", "thursday" -> DayOfWeek.THURSDAY;
        case "fri", "friday" -> DayOfWeek.FRIDAY;
        case "sat", "saturday" -> DayOfWeek.SATURDAY;
        case "sun", "sunday" -> DayOfWeek.SUNDAY;
        default -> null;
        };
    }

    private static LocalTime parseTime(String input) {
        Matcher matcher = TIME_24_HH_COLON_MM.matcher(input);
        if (matcher.matches()) {
            String numeric = matcher.group(1);
            int colon = numeric.indexOf(':');
            int hour = Integer.parseInt(numeric.substring(0, colon));
            int minute = Integer.parseInt(numeric.substring(colon + 1));
            return LocalTime.of(hour, minute);
        }

        matcher = TIME_12_HH_COLON_MM_AP.matcher(input);
        if (matcher.matches()) {
            String numeric = matcher.group(1);
            int colon = numeric.indexOf(':');
            int hour12 = Integer.parseInt(numeric.substring(0, colon));
            int minute = Integer.parseInt(numeric.substring(colon + 1));
            int hour = hour12 % 12 + (matcher.group(2).equals("pm") ? 12 : 0);
            return LocalTime.of(hour, minute);
        }

        if (TIME_NOON.matcher(input).matches()) {
            return LocalTime.NOON;
        }

        if (TIME_MIDNIGHT.matcher(input).matches()) {
            return LocalTime.MIDNIGHT;
        }

        return null;
    }
}
