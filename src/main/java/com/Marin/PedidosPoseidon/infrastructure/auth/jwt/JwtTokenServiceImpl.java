package com.Marin.PedidosPoseidon.infrastructure.auth.jwt;

import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.service.TokenService;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.JwtToken;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.TokenExpiration;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class JwtTokenServiceImpl implements TokenService {

    private final SecretKey secretKey;
    private final int accessTokenExpirationMinutes;
    private final int refreshTokenExpirationDays;

    public JwtTokenServiceImpl(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-expiration-minutes:15}") int accessTokenExpirationMinutes,
            @Value("${app.jwt.refresh-token-expiration-days:7}") int refreshTokenExpirationDays) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationDays = refreshTokenExpirationDays;
    }

    @Override
    public AuthToken generateTokens(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        return AuthToken.builder()
                .userId(user.getId())
                .accessToken(new JwtToken(accessToken))
                .refreshToken(new JwtToken(refreshToken))
                .accessTokenExpiration(TokenExpiration.fromMinutes(accessTokenExpirationMinutes))
                .refreshTokenExpiration(TokenExpiration.fromDays(refreshTokenExpirationDays))
                .build();
    }

    @Override
    public Optional<String> extractUsername(JwtToken token) {
        try {
            Claims claims = extractAllClaims(token);
            return Optional.of(claims.getSubject());
        } catch (Exception e) {
            log.debug("Error extracting username from token", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> extractRole(JwtToken token) {
        try {
            Claims claims = extractAllClaims(token);
            return Optional.ofNullable(claims.get("role", String.class));
        } catch (Exception e) {
            log.debug("Error extracting role from token", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean validateToken(JwtToken token, String username) {
        try {
            Optional<String> extractedUsername = extractUsername(token);
            return extractedUsername.isPresent()
                    && extractedUsername.get().equals(username)
                    && !isTokenExpired(token);
        } catch (Exception e) {
            log.debug("Error validating token", e);
            return false;
        }
    }

    @Override
    public AuthToken refreshTokens(String refreshToken, User user) {
        // Generar nuevos tokens
        return generateTokens(user);
    }

    private String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getValue());
        claims.put("email", user.getEmail());
        claims.put("type", "access");

        return createToken(claims, user.getUsername(), accessTokenExpirationMinutes * 60 * 1000L);
    }

    private String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return createToken(claims, user.getUsername(), refreshTokenExpirationDays * 24 * 60 * 60 * 1000L);
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private Claims extractAllClaims(JwtToken token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token.getValue())
                .getPayload();
    }

    private boolean isTokenExpired(JwtToken token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

}
