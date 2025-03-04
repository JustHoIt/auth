package com.fc.auth.controller;

import com.fc.auth.model.Department;
import com.fc.auth.service.DepartmentService;
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
@RequestMapping("/v1/department")
@RequiredArgsConstructor
@Tag(name="Basics",  description = "기본 관리 API")
public class DepartmentController {

    private final DepartmentService departmentService;


    @Operation(description = "전사 부서 조화")
    @GetMapping(value = "/list",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> listAll(){
        return new ResponseEntity<>(departmentService.listDepartments(), HttpStatus.OK);
    }
}
