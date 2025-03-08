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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                if(Employee.isHR(employee)) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
                Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password", authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("🔐 인증 성공: SecurityContextHolder 에 인증 정보 저장 완료 -> {}", authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
