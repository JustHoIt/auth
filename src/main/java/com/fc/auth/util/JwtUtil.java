package com.fc.auth.util;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.model.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JwtUtil {

    //    private static String SECRET; // 최소 32바이트 필요
//    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final long expirationTimeMills = 1000 * 60 * 60 * 3;

    private static String SECRET = "your-256-bit-secret-your-256-bit-secret"; //나중에 환경변수로 수정
    private static SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    @PostConstruct
    public void init() {
        this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public static String createToken(Employee employee) {
        Date now = new Date();
        Date expireAt = new Date(now.getTime() + expirationTimeMills);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", employee.getEmail());

        if (employee.getRoles() != null && !employee.getRoles().isEmpty()) {
            claims.put("roles", employee.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
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
}
