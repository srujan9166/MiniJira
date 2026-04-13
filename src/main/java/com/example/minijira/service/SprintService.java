package com.example.minijira.service;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.minijira.dto.issueDTO.CreateResponseDTO;
import com.example.minijira.dto.sprintDTO.AllSprintsDTO;
import com.example.minijira.dto.sprintDTO.CreateSprintDTO;
import com.example.minijira.dto.sprintDTO.CreateSprintResponseDTO;
import com.example.minijira.dto.sprintDTO.UpdateSprintRequestDTO;
import com.example.minijira.enums.SprintStatus;
import com.example.minijira.model.Issue;
import com.example.minijira.model.Project;
import com.example.minijira.model.Sprint;
import com.example.minijira.repository.IssueRepository;
import com.example.minijira.repository.ProjectRepository;
import com.example.minijira.repository.SprintRepository;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    public SprintService(SprintRepository sprintRepository , ProjectRepository projectRepository , IssueRepository issueRepository){
        this.sprintRepository = sprintRepository;
        this.projectRepository= projectRepository;
        this.issueRepository = issueRepository;
    }

    public  List<AllSprintsDTO> getSprintsByProjectId(Long id) {
        List<Sprint> sprints = sprintRepository.findByProjectId(id)
                    .orElseThrow(() -> new RuntimeException("Sprints Not Found!"));

                return sprints
                        .stream()
                        .map(sprint -> new AllSprintsDTO(sprint.getName()))
                        .toList();
        
        
    }

    public  CreateSprintResponseDTO createSprint(Long id , CreateSprintDTO createSprintDTO) {
        Project project = projectRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Project Not Found!"));
       Sprint sprint = sprintRepository.save(Sprint.builder()
                .name(createSprintDTO.getName())
                .startDate(createSprintDTO.getStartDate())
                .endDate(createSprintDTO.getEndDate())
               .sprintStatus(createSprintDTO.getStatus())
                .project(project)
                .build());

                return CreateSprintResponseDTO.builder()
                        .id(sprint.getId())
                        .name(sprint.getName())
                        .projectName(project.getName())
                        .startDate(sprint.getStartDate())
                        .endDate(sprint.getEndDate())
                        .build();
            


       
    }

    public  String updateSprint(Long id, UpdateSprintRequestDTO updateSprintRequestDTO) {

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint Not Found!"));

                sprint.setName(updateSprintRequestDTO.getName());
                sprint.setStartDate(updateSprintRequestDTO.getStartDate());
                sprint.setEndDate(updateSprintRequestDTO.getEndDate());
                sprint.setSprintStatus(updateSprintRequestDTO.getSprintStatus());
             Project project=   projectRepository.findById(updateSprintRequestDTO.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project Not Found!"));
                sprint.setProject(project);


        sprintRepository.save(sprint);
        return "Sprint updated successfully!" ;
        
    }

    public void deleteSprint(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint Not Found!"));
        if(LocalDate.now().isBefore(sprint.getStartDate())){
            sprintRepository.delete(sprint);
        }else{
            throw new RuntimeException("Cannot delete a sprint that has already started!");
        }
    }

    public  List<AllSprintsDTO> getAllSprints() {
        List<Sprint> sprints = sprintRepository.findAll();
        return sprints
                .stream()
                .map(sprint -> new AllSprintsDTO(sprint.getName()))
                .toList();
    
    }

    public  CreateSprintDTO startSprint(Long id) {

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint Not Found!"));

        if (sprint.getSprintStatus() != SprintStatus.ACTIVE && sprint.getSprintStatus() != SprintStatus.COMPLETED) {
            sprint.setSprintStatus(SprintStatus.ACTIVE);
            sprintRepository.save(sprint);
            return CreateSprintDTO.builder()
                    .name(sprint.getName())
                    .startDate(sprint.getStartDate())
                    .endDate(sprint.getEndDate())
                    .status(sprint.getSprintStatus())
                    .build();
        } else {
            throw new RuntimeException("Sprint is already in progress or completed!");
        }
        
    }

    public  CreateSprintDTO completeSprint(Long id) {

        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint Not Found!"));

        if (sprint.getSprintStatus() == SprintStatus.ACTIVE) {
            sprint.setSprintStatus(SprintStatus.COMPLETED);
            sprintRepository.save(sprint);
            return CreateSprintDTO.builder()
                    .name(sprint.getName())
                    .startDate(sprint.getStartDate())
                    .endDate(sprint.getEndDate())
                    .status(sprint.getSprintStatus())
                    .build();
        } else {
            throw new RuntimeException("Only active sprints can be completed!");
        }
       
    }

  @Transactional
public void addIssuesToSprint(Long sprintId, List<Long> issueIds) {
    

    Sprint sprint = sprintRepository.findById(sprintId)
            .orElseThrow(() -> new RuntimeException("Sprint not found"));
     if (sprint.getSprintStatus() == SprintStatus.COMPLETED) {
    throw new RuntimeException("Cannot add issues to completed sprint");
        }


    List<Issue> issues = issueRepository.findAllById(issueIds);


    for (Issue issue : issues) {

        
       
        if (issue.getSprint() != null) {
            throw new RuntimeException("Issue already assigned to another sprint");
        }

        issue.setSprint(sprint);
    }

    issueRepository.saveAll(issues);
}

  @Transactional
public void removeIssueFromSprint(Long sprintId, Long issueId) {

    Sprint sprint = sprintRepository.findById(sprintId)
            .orElseThrow(() -> new RuntimeException("Sprint not found"));

    Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));

   
    if (issue.getSprint() == null || !issue.getSprint().getId().equals(sprintId)) {
        throw new RuntimeException("Issue does not belong to this sprint");
    }

    
    issue.setSprint(null);

    issueRepository.save(issue);
}



}
