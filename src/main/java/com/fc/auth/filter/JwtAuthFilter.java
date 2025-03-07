package com.fc.auth.filter;

import com.fc.auth.model.dto.response.KakaoUserInfoResponseDto;
import com.fc.auth.model.entity.Employee;
import com.fc.auth.repository.EmployeeRepository;
import com.fc.auth.service.KakaoService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final KakaoService kakaoService;

    private final EmployeeRepository employeeRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String nickname = kakaoService.getUserFromKakao(token).getKakaoAccount().getProfile().getNickname();

            if(employeeRepository.existsByNickname(nickname)) {
                Employee employee = employeeRepository.findByNickname(nickname);
                Authentication authentication = new TestingAuthenticationToken(employee.getEmail(), "password", "ROLE_TEST");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);
    }
}
