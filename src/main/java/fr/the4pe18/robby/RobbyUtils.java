package fr.the4pe18.robby;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author 4PE18
 */
public class RobbyUtils {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss", Locale.FRANCE).withZone(ZoneId.of("Europe/Paris"));

    public static String getExceptionTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String exception = stringWriter.toString();
        return exception.length() > 799 ? exception.substring(0, 800) + "..." : exception;
    }

    public static String getInstant() {
        return formatter.format(Instant.now());
    }

}
