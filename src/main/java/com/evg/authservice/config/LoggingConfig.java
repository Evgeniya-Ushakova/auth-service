package com.evg.authservice.config;

import com.evg.authservice.utils.MDCInit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import static com.evg.authservice.utils.MdcUtils.HEADER_REQUEST_ID;
import static com.evg.authservice.utils.Utils.getRequestBodyAsString;
import static java.lang.Boolean.TRUE;

@Configuration
@Slf4j(topic = "LOGGING_CONFIG")
@RequiredArgsConstructor
public class LoggingConfig {

    private static final int MAX_PAYLOAD_LENGTH = 10000;

    @Bean
    public CommonsRequestLoggingFilter loggerRequestFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(TRUE);
        filter.setIncludePayload(TRUE);
        filter.setIncludeHeaders(TRUE);
        filter.setIncludeClientInfo(TRUE);
        filter.setMaxPayloadLength(MAX_PAYLOAD_LENGTH);
        return filter;
    }

//    public FilterRegistrationBean loggingFilterRegistration() {
//        FilterRegistrationBean
//    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistration(CommonsRequestLoggingFilter loggerRequestFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(loggerRequestFilter);
        registrationBean.addUrlPatterns("/auth/*");
        return registrationBean;
    }

    @Bean
    public CommonsResponseLoggingFilter loggerResponseFilter() {
        return new CommonsResponseLoggingFilter();
    }

    @Slf4j(topic = "COMMONS_RESPONSE_LOGGING_FILTER")
    static class CommonsResponseLoggingFilter extends GenericFilterBean {

        @SneakyThrows
        @Override
        public void doFilter(ServletRequest request,
                             ServletResponse response,
                             FilterChain filterChain) throws IOException, ServletException {
            var requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
            var responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
            var mdcInit = new MDCInit(extractRequestId(requestWrapper));

            try (mdcInit) {
                logRequest(requestWrapper);
                filterChain.doFilter(requestWrapper, responseWrapper);
                logResponse(requestWrapper, responseWrapper);
            } catch (Exception e) {
                LOGGER.error(String.format("Error at request method to path: %s", ((HttpServletRequest) request).getServletPath()), e);
                throw e;
            } finally {
                responseWrapper.copyBodyToResponse();
            }

        }

        @SneakyThrows
        private void logRequest(ContentCachingRequestWrapper requestWrapper) {
//            if(isAllowedLog(requestWrapper)) {
            LOGGER.info("""
                            \nBefore request:\s
                            url: {},\s
                            params: {},\s
                            method: {}, \s
                            body: {}""",
                    requestWrapper.getRequestURI(),
                    getQueryString(requestWrapper),
                    requestWrapper.getMethod(),
                    getRequestBodyAsString(requestWrapper));
            //           }
        }

        private void logResponse(ContentCachingRequestWrapper requestWrapper,
                                 ContentCachingResponseWrapper responseWrapper) {
//            if(isNeedSkipResponseBody(requestWrapper)) {
//
//            }
            String responseString = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            LOGGER.info("""
                            \nAfter request. Response:\s
                            url: {},\s
                            params: {}\s
                            body: {}""",
                    requestWrapper.getRequestURI(),
                    requestWrapper.getMethod(),
                    responseString);
        }

//       private boolean isAllowedLog(ContentCachingRequestWrapper requestWrapper) {
//            return !excludeFromFilter(requestWrapper.getServletPath()) && !isOctetStream(requestWrapper.getContentType());
//       }

//       private boolean excludeFromFilter(String path) {
//            PathMatcher pathMatcher = new AntPathMatcher();
//
//            return pathMatcher.match("")
//       }

//        private boolean isNeedSkipResponseBody(HttpServletRequest servletRequest) {
//            return StringUtils.equals(servletRequest.getMethod(), "GET"); пока запросы малелькие - можно все логгировать
//        }

        private boolean isOctetStream(String contentType) {
            return Objects.equals(contentType, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }

        private String getQueryString(ContentCachingRequestWrapper requestWrapper) {
            return StringUtils.isBlank(requestWrapper.getQueryString()) ?
                    StringUtils.EMPTY : URLDecoder.decode(requestWrapper.getQueryString(), StandardCharsets.UTF_8);
        }

        private String extractRequestId(ContentCachingRequestWrapper requestWrapper) {
            String traceRequestId = requestWrapper.getHeader(HEADER_REQUEST_ID);
            return StringUtils.isBlank(traceRequestId) ? UUID.randomUUID().toString() : traceRequestId;
        }
    }


}
