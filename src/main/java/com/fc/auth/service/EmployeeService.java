package com.fc.auth.service;

import com.fc.auth.model.Employee;
import com.fc.auth.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> listEmployees(){
        return employeeRepository.findAll();
    }
}
