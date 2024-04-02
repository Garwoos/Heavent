package org.example.heaventfx;


import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateUtils {
    private static DateTimeFormatter dateFormatter;

    public static DateTimeFormatter getDateFormatter() {
        if (dateFormatter == null) {
            dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.FRENCH);
        }
        return dateFormatter;
    }
}