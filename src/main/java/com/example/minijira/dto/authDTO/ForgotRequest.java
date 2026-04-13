package com.example.minijira.dto.authDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;

}
