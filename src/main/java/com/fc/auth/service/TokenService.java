package com.fc.auth.service;

import com.fc.auth.model.dto.response.AppTokenResponseDto;
import com.fc.auth.model.entity.App;
import com.fc.auth.repository.AppRepository;
import com.fc.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AppRepository appRepository;

    public AppTokenResponseDto createAppToken(Long appId) {
        App app = appRepository.findById(appId).orElse(null);
        String token = JwtUtil.createAppToken(app);

        return AppTokenResponseDto.builder()
                .token(token)
                .build();
    }
}
