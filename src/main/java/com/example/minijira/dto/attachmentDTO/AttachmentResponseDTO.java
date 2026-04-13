package com.example.minijira.dto.attachmentDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class AttachmentResponseDTO {

    
    private Long id;
    private String fileName;
    private String filePath;
    private String  issueName;


}
