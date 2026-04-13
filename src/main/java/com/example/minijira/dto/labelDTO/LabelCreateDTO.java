package com.example.minijira.dto.labelDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelCreateDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String color;
    @NotNull
    private Long projectId;

}
