package com.fc.auth.service;

import com.fc.auth.model.dto.response.KakaoUserInfoResponseDto;
import com.fc.auth.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final KakaoService kakaoService;

    private final EmployeeRepository employeeRepository;


    public ResponseEntity login(String code) {
        String token = kakaoService.getAccessTokenFromKakao(code);
        log.info("token : {}", token);

        return new ResponseEntity(token, HttpStatus.OK);
    }


    public ResponseEntity getKakaoUser(String token) {
        KakaoUserInfoResponseDto dto = kakaoService.getUserFromKakao(token);
        String nickname = dto.getKakaoAccount().getProfile().getNickname();
        if (employeeRepository.existsByEmail(dto.getKakaoAccount().getEmail())) {
            return new ResponseEntity("환영합니다 " + nickname, HttpStatus.OK);
        } else {
            return new ResponseEntity("등록된 임직원이 아닙니다.", HttpStatus.FORBIDDEN);
        }
    }
}
