package com.demo.backend.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final static List<String> DOCUMENT_ENDPOINT_PATTERNS = List.of(
            "/api/policy/(.*)/document/(.*)",
            "/api/claim/(.*)/document/(.*)",
            "/api/chat/message/(.*)/document/(.*)"
    );
    private final static String COOKIE_STUDENT_AUTH_NAME = "studentAuthorizationToken";
    private final static String COOKIE_LECTURER_AUTH_NAME = "lecturerAuthorizationToken";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        final String token;
        if (DOCUMENT_ENDPOINT_PATTERNS.stream().anyMatch(path -> request.getRequestURI().matches(path)) && request.getMethod().equals(RequestMethod.GET.name())) {
            token = Optional.ofNullable(request.getCookies())
                    .flatMap(cookies ->
                            Arrays.stream(request.getCookies())
                                    .filter(cookie -> COOKIE_STUDENT_AUTH_NAME.equals(cookie.getName()) || COOKIE_LECTURER_AUTH_NAME.equals(cookie.getName()))
                                    .findAny()
                                    .map(Cookie::getValue)
                    ).orElseGet(() -> jwtTokenProvider.resolveToken(request));
        } else {
            token = jwtTokenProvider.resolveToken(request);
        }

        if (token != null) {
            if (jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
