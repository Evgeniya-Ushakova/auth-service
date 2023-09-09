package com.evg.authservice.config;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "AUTH_MANAGER")
public class AdminAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            if (authentication instanceof TokenAuthentication) {
                return processAuthentication((TokenAuthentication) authentication);
            }
            return null;
        } catch (Exception e) {
            throw e;
        }

    }

    private Authentication processAuthentication(TokenAuthentication authentication) {
        if (StringUtils.isBlank(authentication.getPrincipal())) {
            throw new BadCredentialsException("Token is null or empty");
        }
        if (authentication.getAuthorities().isEmpty()) {
            throw new BadCredentialsException("Not found user with this credentials");
        }
        return authentication;
    }
}
