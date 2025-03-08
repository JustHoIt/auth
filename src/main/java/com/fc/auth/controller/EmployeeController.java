package com.fc.auth.controller;

import com.fc.auth.model.entity.Employee;
import com.fc.auth.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Tag(name = "Basics", description = "기본 관리 API")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(description = "전사원 리스트")
    @GetMapping(value = "/employees",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> listAll() {
        log.info("EmployeeController.listAll");
        return new ResponseEntity<>(employeeService.listEmployees(), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/createEmployee",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createUser(@RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName,
                                               @RequestParam("departmentId") Long departmentId,
                                               @RequestParam("nickname") String nickname,
                                               @RequestParam("email") String email) {
        Employee employee = employeeService.createEmployee(firstName, lastName, departmentId, nickname, email);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
}
