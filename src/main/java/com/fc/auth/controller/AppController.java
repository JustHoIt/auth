package com.fc.auth.controller;

import com.fc.auth.model.entity.App;
import com.fc.auth.service.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/v1/admin")
@RequiredArgsConstructor
@Tag(name="Basics", description = "기본 관리 API")
public class AppController {

    private final AppService appService;


    @Operation(description = "")
    @GetMapping(value = "/apps",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<App>> listAll(){
        return new ResponseEntity<>(appService.listApps(), HttpStatus.OK);
    }
}
