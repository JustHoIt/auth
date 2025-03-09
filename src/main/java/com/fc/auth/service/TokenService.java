package com.fc.auth.service;

import com.fc.auth.model.dto.request.ValidateTokenRequestDto;
import com.fc.auth.model.dto.response.AppTokenResponseDto;
import com.fc.auth.model.entity.Api;
import com.fc.auth.model.entity.App;
import com.fc.auth.repository.ApiRepository;
import com.fc.auth.repository.AppRepository;
import com.fc.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AppRepository appRepository;
    private final ApiRepository apiRepository;

    public AppTokenResponseDto createAppToken(Long appId) {
        App app = appRepository.findById(appId).orElse(null);
        String token = JwtUtil.createAppToken(app);

        return AppTokenResponseDto.builder()
                .token(token)
                .build();
    }

    public ResponseEntity<String> validateToken(ValidateTokenRequestDto dto) {
        Api api = apiRepository.findByMethodAndPath(dto.getMethod(), dto.getPath());
        return JwtUtil.validateAppToken(dto, api);
    }
}
