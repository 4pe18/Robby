package fr.the4pe18.Robby;

import java.io.PrintWriter;
import java.io.StringWriter;

public class RobbyUtils {
    public static String getExceptionTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string
        return sStackTrace.substring(0, 800) + "...";
    }
}
