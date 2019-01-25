package com.hawkore.mule.apacheignite.example.rt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class Utils {
    
    private static final Logger logger = Logger.getLogger(Utils.class);

    public static InputStream stream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    public static String fail(String v) {
        if ("true".equals(v)) {
            throw new ForcedFailException();
        }
        return "ok";
    }

    public static boolean isIllegalStateException(final org.mule.runtime.api.message.Error e) {
        return e != null && e.getDescription() != null && e.getDescription().contains("ForcedFailException");
    }

    public static String sleep(long millis) throws InterruptedException {
        logger.debug("sleep: waiting " + millis + " milliseconds at thread " + Thread.currentThread().getName());
        Thread.sleep(millis);
        return "ok";
    }

    public static String toString(Object o) {
        return o == null ? null : o.toString();
    }

    public static String concat(Object o, Object o2) {
        return (o == null ? null : o.toString()) + "-" + (o2 == null ? null : o2.toString());
    }

    public static class ForcedFailException extends RuntimeException {

        /**
         * 
         */
        private static final long serialVersionUID = -7510513604825311435L;

    }
}
