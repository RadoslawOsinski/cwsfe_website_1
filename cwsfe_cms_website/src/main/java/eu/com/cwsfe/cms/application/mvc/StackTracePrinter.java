package eu.com.cwsfe.cms.application.mvc;

import java.io.PrintWriter;
import java.io.StringWriter;

class StackTracePrinter {

    public String getStackTraceForHtml(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String stackTrace = sw.toString();
        return stackTrace.replace(System.getProperty("line.separator"), "<br/>\n");
    }

}
