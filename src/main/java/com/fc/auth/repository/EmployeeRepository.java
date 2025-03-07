package com.fc.auth.repository;

import com.fc.auth.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);

    Employee findByNickname(String nickname);
}
