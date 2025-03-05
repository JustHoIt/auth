package com.fc.auth.controller;

import com.fc.auth.model.dto.response.KakaoUserInfoResponseDto;
import com.fc.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public ResponseEntity callback(@RequestParam("code") String code) {
        String token = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto dto = kakaoService.getUserFromKakao(token);
        log.info(dto.toString());
        return new ResponseEntity(HttpStatus.OK);
    }


}
