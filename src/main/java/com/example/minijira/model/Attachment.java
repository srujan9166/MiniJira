package com.example.minijira.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_path", nullable = false)
    private String filePath;
    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;
    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploader;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


}
