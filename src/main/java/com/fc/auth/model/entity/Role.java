package com.fc.auth.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "auto increment pk")
    private Long id;

    @Schema(example = "인사팀", description = "권한 이름")
    private String name;



}
