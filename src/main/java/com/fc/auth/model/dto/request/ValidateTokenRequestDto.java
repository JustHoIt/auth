package com.fc.auth.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateTokenRequestDto {
    private String token;

    private Long app;

    private String method;

    private String path;
}
