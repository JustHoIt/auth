package com.fc.auth.service;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.repository.EmployeeRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> listEmployees() {
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
}
