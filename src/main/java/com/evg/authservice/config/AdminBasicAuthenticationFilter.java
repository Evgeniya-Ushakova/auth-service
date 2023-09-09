package com.evg.authservice.config;

import com.evg.authservice.entity.User;
import com.evg.authservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Component
@Slf4j(topic = "AUTHENTICATION_FILTER.BASIC")
public class AdminBasicAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private UserRepository userRepository;

    public AdminBasicAuthenticationFilter(AdminAuthenticationManager adminAuthenticationManager,
                                          UserRepository userRepository) {
        super("/**");
        this.userRepository = userRepository;

        setSessionAuthenticationStrategy((authentication, reauest, response) ->
                SecurityContextHolder.getContext().setAuthentication(authentication));
        setAuthenticationSuccessHandler((request, response, authentication) -> {});
//        setAuthenticationFailureHandler((request, response, authentication) ->
//                handleAuthenticationException(response, authentication));
        setAuthenticationManager(adminAuthenticationManager);
        setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/auth/login")));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.isBlank(token)) {
            throw new BadCredentialsException("Authorization header is empty");
        }

        User user = findUser(token);
        if(Objects.nonNull(user)) {
            return new TokenAuthentication(user.getEmail(), List.of(new SimpleGrantedAuthority("user")));
        }

        throw new BadCredentialsException("Email or password incorrect");
    }

    private User findUser(String token) {
        String[] credentials = new String(Base64.getDecoder().decode(token.split(" ")[1]), StandardCharsets.ISO_8859_1).split(":");
        String email = credentials[0];
        String password = credentials[1];

        return userRepository.findUserByEmailAndPassword(email, password);
    }

}
