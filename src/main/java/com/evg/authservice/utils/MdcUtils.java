package com.evg.authservice.utils;

import org.slf4j.MDC;

public class MdcUtils {

    public static final String MDC_REQUEST_ID = "requestId";
    public static final String HEADER_REQUEST_ID = "X-B3-RequestId";

    public static String getMdcRequestId() {
        return MDC.get(MDC_REQUEST_ID);
    }

}
