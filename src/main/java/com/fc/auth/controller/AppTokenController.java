package com.fc.auth.controller;

import com.fc.auth.model.dto.request.ValidateTokenRequestDto;
import com.fc.auth.model.dto.response.AppTokenResponseDto;
import com.fc.auth.service.TokenService;
import com.fc.auth.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/token")
@Tag(name = "App2app Token", description = "app2app token API")
public class AppTokenController {

    private final TokenService tokenService;

    @Operation(description = "토큰 발급")
    @PostMapping(value = "/new/{appId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppTokenResponseDto> createNewAppToken(@PathVariable("appId") Long appId) {
        AppTokenResponseDto dto = tokenService.createAppToken(appId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(description = "토큰 밸리데이션")
    @PostMapping(value = "/validate",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateAppToken(ValidateTokenRequestDto dto){

        return tokenService.validateToken(dto);
    }

}
