package com.fc.auth.util;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.model.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Test
    @DisplayName("토큰 발급 및 정보 일치 테스트")
    void test() {
        String testEmail = "abc123@abc.com";
        Employee employee = Employee.builder()
                .firstName("홍")
                .lastName("길동")
                .departmentId(1L)
                .email(testEmail)
                .nickname("테스트닉네임")
                .build();

        String token = JwtUtil.createToken(employee);

        assertEquals(testEmail, JwtUtil.parseToken(token).get("email"));
    }

    @Test
    @DisplayName("Role Test")
    void test_role() {
        Role role1 = Role.builder()
                .id(1L)
                .name("role1")
                .build();
        Role role2 = Role.builder()
                .id(2L)
                .name("role2")
                .build();

        List<Role> roles = Arrays.asList(role1, role2);
        Set<Role> roleSet = new HashSet<>(roles);

        Employee employee = Employee.builder()
                .roles(roleSet)
                .build();

        String token = JwtUtil.createToken(employee);
        List res = JwtUtil.parseToken(token).get("roles", List.class);

        assertEquals(roleSet.size(), res.size());
        assertTrue(res.contains(role1.getName()));
        assertTrue(res.contains(role2.getName()));
    }
}