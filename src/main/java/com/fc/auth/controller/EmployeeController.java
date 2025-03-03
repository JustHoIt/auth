package com.fc.auth.controller;

import com.fc.auth.model.Employee;
import com.fc.auth.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@RequiredArgsConstructor
@Tag(name="Basics",  description = "기본 관리 API")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(description = "전사 ")
    @GetMapping(value = "/list",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> listAll(){
        return new ResponseEntity<>(employeeService.listEmployees(), HttpStatus.OK);
    }
}
