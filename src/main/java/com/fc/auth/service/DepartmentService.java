package com.fc.auth.service;

import com.fc.auth.model.entity.Department;
import com.fc.auth.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> listDepartments(){

        return departmentRepository.findAll();
    }
}
