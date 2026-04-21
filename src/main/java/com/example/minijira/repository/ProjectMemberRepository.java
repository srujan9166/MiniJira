package com.example.minijira.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.minijira.model.ProjectMember;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    boolean existsByProjectIdAndUserId(Long id, Long userId);
    Set<ProjectMember> findByProjectId(Long id);

    @Modifying
@Query("DELETE FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.user.id = :userId")
void deleteMember(Long projectId, Long userId);
    Optional<ProjectMember> findByProjectIdAndUserId(Long id, Long userId);

}
