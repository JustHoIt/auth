package com.fc.auth.service;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.repository.ApiRepository;
import com.fc.auth.repository.EmployeeRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ApiRepository apiRepository;

    public List<Employee> listEmployees() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Security context is not authenticated");
            throw new SecurityException("접근 권한이 없습니다.");
        }

        String name = authentication.getName();
        log.info("Security Context Holder name : {}", name);
        return employeeRepository.findAll();
    }

    public Employee createEmployee(String firstName, String lastName, Long departmentId, String nickname, String email) {
        if (employeeRepository.existsByNickname(nickname)) {
            throw new DuplicateRequestException("중복된 닉네임입니다.");
        } else if (employeeRepository.existsByEmail(email)) {
            throw new DuplicateRequestException("중복된 이메일입니다.");
        }

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .departmentId(departmentId)
                .nickname(nickname)
                .email(email)
                .build();
        employeeRepository.save(employee);

        return employee;
    }

    @Cacheable(cacheNames = "employee", key = "#id")
    public Employee findEmployee(Long id) {
        log.info("service : {} ", id);
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("id를 찾을수 없습니다"));
    }
}
