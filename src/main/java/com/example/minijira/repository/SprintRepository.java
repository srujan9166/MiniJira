package com.example.minijira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minijira.model.Sprint;

@Repository
public interface SprintRepository  extends JpaRepository<Sprint, Long> {
   Optional<List<Sprint>> findByProjectId(Long projectId);

}
