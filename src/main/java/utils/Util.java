package utils;

import java.time.format.DateTimeFormatter;

public class Util {
    public static final String COLON = ":";
    public static final String QUOTE_FORMAT = "\"%s\"";
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

}
