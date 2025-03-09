package com.fc.auth.util;

import com.fc.auth.model.dto.request.ValidateTokenRequestDto;
import com.fc.auth.model.entity.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtUtil {

    private static final long expirationTimeMills = 1000 * 60 * 60 * 3;

    private static final String SECRET = "your-256-bit-secret-your-256-bit-secret"; //나중에 환경변수로 수정
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String createAppToken(App app) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expirationTimeMills);


        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "app");
        claims.put("roles", app.getAppRoles().stream().map(AppRole::getApi).map(Api::getId).collect(Collectors.toSet()));

        return Jwts.builder()
                .subject(String.valueOf(app.getId()))
                .claims(claims)
                .issuedAt(new Date())         // 토큰 생성 시간
                .expiration(expireAt)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String createUserToken(Employee employee) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expirationTimeMills);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", employee.getEmail());

        if (employee.getEmployeeRoles() != null && !employee.getEmployeeRoles().isEmpty()) {
            claims.put("roles", employee.getEmployeeRoles().stream().map(EmployeeRole::getName).collect(Collectors.toSet()));
        } else {
            claims.put("roles", Collections.emptySet());
        }

        return Jwts.builder()
                .subject(String.valueOf(employee.getId()))
                .claims(claims)
                .issuedAt(new Date())         // 토큰 생성 시간
                .expiration(expireAt)
                .signWith(SECRET_KEY)
                .compact();
    }


    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static ResponseEntity<String> validateAppToken(ValidateTokenRequestDto dto, Api api) {
        Claims claims;
        try {
            claims = parseToken(dto.getToken());
        } catch (Exception e) {
            return new ResponseEntity<>("invalid token", HttpStatus.UNAUTHORIZED);
        }

        Date now = new Date();
        if (claims.getExpiration().before(now)) {
            return new ResponseEntity<>("token expired.", HttpStatus.UNAUTHORIZED);
        }
        if (!StringUtils.equals("app", claims.get("type").toString())) {
            return new ResponseEntity<>("invalid token type", HttpStatus.UNAUTHORIZED);
        }

        String roles = claims.get("roles").toString();
        if (roles.contains(api.getId().toString())) {
            return new ResponseEntity<>("권한이 존재합니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);

        }
    }
}
