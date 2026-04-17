package com.example.minijira.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.projectDTO.AddMemberDTO;
import com.example.minijira.dto.projectDTO.ProjectRequestDTO;
import com.example.minijira.dto.projectDTO.ProjectResponseDTO;
import com.example.minijira.dto.projectDTO.UpdateMemberDTO;
import com.example.minijira.model.Project;
import com.example.minijira.model.User;
import com.example.minijira.repository.ProjectRepository;
import com.example.minijira.service.AuthServiceImpl;
import com.example.minijira.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
   
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
        
    }
     @GetMapping
    public Page<ProjectResponseDTO> getProjects(

        @RequestParam(defaultValue="0")
        int page,

        @RequestParam(defaultValue="5")
        int size,

        @RequestParam(defaultValue="id")
        String sortBy
    ){

        return projectService.getProjectsByPage(
                page,
                size,
                sortBy
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDTO projectRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectRequestDTO));
    }
    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects(){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectDetails(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectDetails(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@RequestBody ProjectRequestDTO projectRequestDTO,@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(projectService.updateProject(projectRequestDTO , id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> archiveProject(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(projectService.archiveProject(id));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<?> addMemberToProject(@PathVariable Long id , @RequestBody AddMemberDTO addMemberDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addMemberToProject(id , addMemberDTO));
    }
    @GetMapping("/{id}/members")
    public ResponseEntity<?> getProjectMembers(@PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectMembers(id));
    }
    @DeleteMapping("{id}/members/{userId}")
    public ResponseEntity<Void> removeProjectMember(@PathVariable Long id , @PathVariable Long userId){
        projectService.removeProjectMember(id,userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PatchMapping("{id}/members/{userId}")
    public ResponseEntity<String> updateMemberRole(@PathVariable Long id , @PathVariable Long userId , @RequestBody UpdateMemberDTO updateMemberDTO ){
        
        return ResponseEntity.status(HttpStatus.OK).body(projectService.updateMemberRole(id , userId , updateMemberDTO));
    }
   @GetMapping("/my-projects")
public ResponseEntity<?> getProjects() {
    return ResponseEntity.ok(projectService.getUserProjects());
}

}
