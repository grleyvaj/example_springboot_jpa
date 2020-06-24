package cu.example.infrastructure.shared;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {

    /**
     * @param date   The date as String
     * @param format The format in which you want to format the date from the DateTimeFormatter constant
     * @return LocalDate The as LocalDateTime
     */
    public static LocalDate convert(String date, DateTimeFormatter format) {
        return LocalDate.parse(date, format);
    }

    /**
     * @param date   The date as LocalDate
     * @param format The format in which you want to format the date from the DateTimeFormatter constant
     * @return String The date as String
     */
    public static String convert(LocalDate date, DateTimeFormatter format) {
        return date.format(format);
    }

    public static String convertToDateTime(LocalDate fecha) {
        return LocalDateTime.ofInstant(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault()).toString();
    }

    /**
     * @param date Date of type Date to be converted to LocalDateTime     *
     * @return LocalDateTime The date converted to LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate convertToLocalDate(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param date Date of type LocalDateTime to be converted to Date
     * @return Date The date converted to Date
     */
    public static Date convert(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convert(LocalDate fecha) {
        return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertToLocalDateTime(String fecha, DateTimeFormatter formato) {
        return LocalDateTime.parse(fecha, formato);
    }

    public static boolean isValid(String fecha, DateTimeFormatter formato) {
        try {
            convert(fecha, formato);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static LocalDate sumDaysDate(int days) {
        Calendar hoy = Calendar.getInstance();
        hoy.add(Calendar.DAY_OF_YEAR, days);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(hoy.toInstant(), hoy.getTimeZone().toZoneId());
        return localDateTime.toLocalDate();
    }

}
