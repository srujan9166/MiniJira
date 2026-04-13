package com.example.minijira.dto.labelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelResponseDTO {
    private Long id;
    private String name;
    private String color;
    private String projectName;

}
