package com.fc.auth.service;

import com.fc.auth.model.dto.response.KakaoTokenResponseDto;
import com.fc.auth.model.dto.response.KakaoUserInfoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class KakaoService {

    @Value("${kakao.client_id}")
    private String clientId;
    @Value("${kakao.redirect_uri}")
    private String redirectUri;


    private final String KAKAO_AUTH_URL = "https://kauth.kakao.com";

    private final String KAKAO_USER_URL = "https://kapi.kakao.com";


    public KakaoUserInfoResponseDto getUserFromKakao(String access_token) {
        return WebClient.create(KAKAO_USER_URL)
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("https")
                                .path("/v2/user/me")
                                .build())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + access_token)
                        .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                        .retrieve()
                        .bodyToMono(KakaoUserInfoResponseDto.class)
                        .block();
    }

    public String getAccessTokenFromKakao(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto =
                WebClient.create(KAKAO_AUTH_URL)
                        .post()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("https")
                                .path("/oauth/token")
                                .queryParam("grant_type", "authorization_code")
                                .queryParam("client_id", clientId)
                                .queryParam("redirect_uri", redirectUri)
                                .queryParam("code", code)
                                .build())
                        .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8")
                        .retrieve()
                        .bodyToMono(KakaoTokenResponseDto.class)
                        .block();

        return kakaoTokenResponseDto.getAccessToken();
    }
}
