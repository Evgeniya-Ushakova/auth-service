package com.evg.authservice.utils;

import org.slf4j.MDC;

import static com.evg.authservice.utils.MdcUtils.MDC_REQUEST_ID;

public class MDCInit implements AutoCloseable{

    public MDCInit(String requestId) {
        MDC.put(MDC_REQUEST_ID, requestId);
    }


    @Override
    public void close() throws Exception {

    }
}
