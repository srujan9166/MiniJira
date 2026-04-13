package com.example.minijira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minijira.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    boolean existsByProjectKey(String projectKey);

    Optional<Project> findByProjectKey(String projectKey);

    Optional<Project> findById(Long id);
    Optional<List<Project>> findByUserId(Long userId);
    
}
