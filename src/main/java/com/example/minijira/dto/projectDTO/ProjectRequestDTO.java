package com.example.minijira.dto.projectDTO;



import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProjectRequestDTO {
    @NotBlank
    private String name ;
    @NotBlank
    private String description;
    
    private String projectKey;

}
