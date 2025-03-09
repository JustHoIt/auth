package com.fc.auth.util;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.model.entity.EmployeeRole;
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

        String token = JwtUtil.createUserToken(employee);

        assertEquals(testEmail, JwtUtil.parseToken(token).get("email"));
    }

    @Test
    @DisplayName("Role Test")
    void test_role() {
        EmployeeRole employeeRole1 = EmployeeRole.builder()
                .id(1L)
                .name("role1")
                .build();
        EmployeeRole employeeRole2 = EmployeeRole.builder()
                .id(2L)
                .name("role2")
                .build();

        List<EmployeeRole> employeeRoles = Arrays.asList(employeeRole1, employeeRole2);
        Set<EmployeeRole> employeeRoleSet = new HashSet<>(employeeRoles);

        Employee employee = Employee.builder()
                .employeeRoles(employeeRoleSet)
                .build();

        String token = JwtUtil.createUserToken(employee);
        List res = JwtUtil.parseToken(token).get("roles", List.class);

        assertEquals(employeeRoleSet.size(), res.size());
        assertTrue(res.contains(employeeRole1.getName()));
        assertTrue(res.contains(employeeRole2.getName()));
    }
}