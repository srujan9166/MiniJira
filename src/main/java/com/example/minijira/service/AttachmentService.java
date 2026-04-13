package com.example.minijira.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.minijira.dto.attachmentDTO.AttachmentResponseDTO;
import com.example.minijira.model.Attachment;
import com.example.minijira.model.Issue;
import com.example.minijira.repository.AttachmentRepository;
import com.example.minijira.repository.IssueRepository;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final IssueRepository issueRepository;
    private final AuthServiceImpl authServiceImpl;
    public AttachmentService(AttachmentRepository attachmentRepository, IssueRepository issueRepository, AuthServiceImpl authServiceImpl) {
        this.attachmentRepository = attachmentRepository;
        this.issueRepository = issueRepository;
        this.authServiceImpl = authServiceImpl;
    }

    public  AttachmentResponseDTO uploadAttachment(Long id, MultipartFile file) {
            Issue issue = issueRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Issue not found with id: " + id));
                
                    String fileName = file.getOriginalFilename();
                    String filePath = "attachments/" + fileName;
                    try {
                        Files.createDirectories(Paths.get("attachments"));

                        Files.copy(file.getInputStream(), Paths.get(filePath));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to upload file: " + e.getMessage());
                    }
                    Attachment attachment = new Attachment();
                    attachment.setFileName(fileName);
                    attachment.setFilePath(filePath);
                    attachment.setIssue(issue);
                    attachment.setUploader(authServiceImpl.getCurrentUser());
                    attachment.setCreatedAt(LocalDateTime.now());

                    attachmentRepository.save(attachment);
                    return convetAttachmentToDTO(attachment);
    }

    public AttachmentResponseDTO convetAttachmentToDTO(Attachment attachment){
        return AttachmentResponseDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .filePath(attachment.getFilePath())
                .issueName(attachment.getIssue().getTitle())
                .build();
    }

    

}
