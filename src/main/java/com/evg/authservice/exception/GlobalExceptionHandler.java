//package com.evg.authservice.exception;
//
//import com.evg.authservice.dto.BaseResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.MissingRequestHeaderException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//
//@RestControllerAdvice
//@RequiredArgsConstructor
//@Slf4j(topic = "EXCEPTION_HANDLER")
//public class GlobalExceptionHandler {
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({Throwable.class})
//    public BaseResponse exceptions(Exception e) {
//        LOGGER.error("Failed", e);
//        return new BaseResponse(999, e.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler({BadCredentialsException.class})
//    public BaseResponse exceptions(BadCredentialsException e) {
//        LOGGER.error("Failed", e);
//        return new BaseResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({MissingRequestHeaderException.class})
//    public BaseResponse exceptions(MissingRequestHeaderException e) {
//        LOGGER.error("Failed", e);
//        return new BaseResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
//    }
//
//}
