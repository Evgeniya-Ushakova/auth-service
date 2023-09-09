package com.evg.authservice.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication extends AbstractAuthenticationToken {

    private final String email;

    public TokenAuthentication(String email, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
    }

    @Override
    public String getCredentials() {
        return email;
    }

    @Override
    public String getPrincipal() {
        return email;
    }
}
