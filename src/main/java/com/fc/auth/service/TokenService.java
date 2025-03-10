package com.fc.auth.service;

import com.fc.auth.config.CustomRateLimiter;
import com.fc.auth.model.dto.request.ValidateTokenRequestDto;
import com.fc.auth.model.dto.response.AppTokenResponseDto;
import com.fc.auth.model.entity.Api;
import com.fc.auth.model.entity.App;
import com.fc.auth.repository.ApiRepository;
import com.fc.auth.repository.AppRepository;
import com.fc.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final AppRepository appRepository;
    private final ApiRepository apiRepository;
    private final CustomRateLimiter customRateLimiter;

    public AppTokenResponseDto createAppToken(Long appId) {
        App app = appRepository.findById(appId).orElse(null);
        String token = JwtUtil.createAppToken(app);

        return AppTokenResponseDto.builder()
                .token(token)
                .build();
    }

    public ResponseEntity<String> validateToken(ValidateTokenRequestDto dto) {
        Api api = apiRepository.findByMethodAndPath(dto.getMethod(), dto.getPath());
        ResponseEntity resp = JwtUtil.validateAppToken(dto, api);
        if (resp.getStatusCode().is2xxSuccessful()) {
            Long appId = Long.valueOf(JwtUtil.parseSubject(dto.getToken()));
            if (!customRateLimiter.tryConsume(appId, api.getId())) {
                log.error("TOO MANY REQUESTS");
                return new ResponseEntity<>("too many requests", HttpStatus.TOO_MANY_REQUESTS);
            }
        }
        return resp;
    }
}
