package com.fc.auth.filter;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.repository.EmployeeRepository;
import com.fc.auth.service.KakaoService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final KakaoService kakaoService;
    private final EmployeeRepository employeeRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            String nickname = kakaoService.getUserFromKakao(accessToken).getKakaoAccount().getProfile().getNickname();
            log.info("name : {}, accessToken : {}", nickname, accessToken);

            if (employeeRepository.existsByNickname(nickname)) {
                Employee employee = employeeRepository.findByNickname(nickname);

                Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password", "ROLE_TEST");
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("🔐 인증 성공: SecurityContextHolder 에 인증 정보 저장 완료 -> {}", authentication);
            } else {
                log.warn("❌ 닉네임이 데이터베이스에 존재하지 않음: {}", nickname);
            }
        } else {
            log.warn("❌ Authorization 헤더가 없거나 올바른 형식이 아님.");
        }

        filterChain.doFilter(request, response);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("🧐 필터 종료 후 SecurityContext Authentication: {}", auth);
    }
}
