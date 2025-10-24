package Proyect.IoTParkers.iam.infrastructure.tokens.jwt.services;

import Proyect.IoTParkers.iam.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Token service implementation for JWT tokens.
 * This class is responsible for generating and validating JWT tokens.
 * It uses the secret and expiration days from the application.properties file.
 */
@Service
public class TokenServiceImpl implements BearerTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int TOKEN_BEGIN_INDEX = 7;

    @Value("${authorization.jwt.secret}")
    private String secret;

    // NUEVO: TTLS parametrizables
    @Value("${authorization.jwt.access-ttl}")
    private Duration accessTtl;   // PT4H

    @Value("${authorization.jwt.refresh-ttl}")
    private Duration refreshTtl;  // P1D


    /** Emite ACCESS token tomando el subject del Authentication. */
    @Override
    public String allocateToken(Authentication authentication) {
        String userId = authentication.getName();
        return allocateAccessToken(userId, Map.of());
    }

    /** Emite ACCESS token para userId directo. */
    @Override
    public String allocateToken(String subject) {
        return allocateAccessToken(subject, Map.of());
    }

    /** Emite ACCESS token con claims extra (ej: scope, roles, etc.). */
    @Override
    public String allocateAccessToken(String userId, Map<String, Object> extraClaims) {
        return buildToken(userId, accessTtl, extraClaims);
    }

    /** Emite REFRESH token (claim typ=refresh). */
    @Override
    public String allocateRefreshToken(String userId) {
        return buildToken(userId, refreshTtl, Map.of("typ", "refresh"));
    }

    /** Devuelve true si el token tiene claim typ=refresh. */
    @Override
    public boolean isRefreshToken(String token) {
        try {
            Object typ = parse(token).get("typ");
            return "refresh".equals(typ);
        } catch (Exception e) {
            return false;
        }
    }

    /** Obtiene el subject (userId) desde el JWT. */
    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** Valida firma/formato/expiración del JWT. */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            LOGGER.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.warn("Invalid JWT: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.info("JWT expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.warn("JWT unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /** Extrae el Bearer token del header Authorization. */
    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(TOKEN_BEGIN_INDEX);
        }
        return null;
    }



    private String buildToken(String userId, Duration ttl, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant exp = now.plus(ttl);
        return Jwts.builder()
                .subject(userId)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(getSigningKey())
                .compact();
    }

    private Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(parse(token));
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}