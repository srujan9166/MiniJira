package com.example.minijira.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minijira.model.Attachment;

@Repository
public interface AttachmentRepository  extends JpaRepository<Attachment, Long> {
    

}
