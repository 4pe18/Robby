package fr.the4pe18.robby;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 4PE18
 */
public class RobbyUtils {

    public static String getExceptionTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(new StringWriter()));
        return stringWriter.toString().substring(0, 800) + "...";
    }

}
