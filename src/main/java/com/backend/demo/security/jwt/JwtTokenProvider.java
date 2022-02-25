package com.backend.demo.security.jwt;

import com.backend.demo.security.JwtUserDetailsService;
import com.backend.demo.model.misc.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String CONTEXT_KEY = "context";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtTokenProvider(@Lazy JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Encoders.BASE64.encode(this.secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId, boolean rememberMe, Role contextRole) {
        Date now = new Date();
        Claims claims = Jwts.claims(Map.of(CONTEXT_KEY, contextRole))
                .setSubject(userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(this.getSigningKey());

        if (!rememberMe) {
            Date validity = new Date(now.getTime() + validityInMilliseconds);
            jwtBuilder.setExpiration(validity);
        }

        return jwtBuilder.compact();
    }

    public Authentication getAuthentication(String token) {
        Claims jwtClaims = getJWTClaims(token);
        String userId = jwtClaims.getSubject();
        Role contextRole = Role.valueOf(jwtClaims.get(CONTEXT_KEY, String.class));

        UserDetails userDetails = jwtUserDetailsService.loadById(userId, contextRole);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        return resolveToken(bearerToken);
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // 7 = string length of "Bearer "
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration() == null || !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims getJWTClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
