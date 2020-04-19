package fr.the4pe18.robby;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

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

    public static String[] getQuotationArgs(String[] spaceArgs) {
        boolean opened = false;
        String[] quotationArgs = new String[spaceArgs.length];
        StringBuilder arg = new StringBuilder();
        int argPos = 0;
        for (String s : spaceArgs) {
            s = s.replace("\\\"", "%quotation_mark%");
            if (!opened) {

                if (s.startsWith("\"")) opened=true;
                arg = new StringBuilder(s.replace("\"", ""));

            } else {

                if (s.endsWith("\"")) opened=false;
                arg.append(arg.length() > 0 && arg.toString().endsWith(" ") ? "" : " ").append(s.replace("\"", ""));

            }
            if (!opened) quotationArgs[argPos++] = arg.toString().replace("%quotation_mark%","\"");
        }
        return Arrays.stream(quotationArgs).filter(Objects::nonNull).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }

    public static void main(String[] args) {
        String s = "bonjour \"je suis un morceau séparé\" \"un autre mor\"ceau\" \"ou celu\"i ci\" 32 \"ou encore \\\"bonjour\\\" voilà\"";
        System.out.println(Arrays.toString(getQuotationArgs(s.split(" "))));
    }

}
