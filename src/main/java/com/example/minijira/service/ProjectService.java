package com.example.minijira.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.weaver.ast.Not;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.minijira.dto.notificationDTO.NotificationSendDTO;
import com.example.minijira.dto.projectDTO.AddMemberDTO;
import com.example.minijira.dto.projectDTO.ProjectDetailsDTO;
import com.example.minijira.dto.projectDTO.ProjectMemberDTO;
import com.example.minijira.dto.projectDTO.ProjectRequestDTO;
import com.example.minijira.dto.projectDTO.ProjectResponseDTO;
import com.example.minijira.dto.projectDTO.UpdateMemberDTO;
import com.example.minijira.enums.ProjectRole;
import com.example.minijira.model.Project;
import com.example.minijira.model.ProjectMember;
import com.example.minijira.model.User;
import com.example.minijira.repository.ProjectMemberRepository;
import com.example.minijira.repository.ProjectRepository;
import com.example.minijira.repository.UserRepository;

@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthServiceImpl authServiceImpl;
    private final NotificationService notificationService;
    private final EmailService emailService;



    public ProjectService(UserRepository userRepository , ProjectRepository projectRepository , ProjectMemberRepository projectMemberRepository, AuthServiceImpl authServiceImpl, NotificationService notificationService, EmailService emailService){
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.authServiceImpl = authServiceImpl;
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO){

      

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username Not Found !"));
        if(projectRepository.existsByProjectKey(projectRequestDTO.getProjectKey())){
            throw new RuntimeException("Try new key!");
        }
        Project project = new Project();
       
        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        project.setProjectKey(projectRequestDTO.getProjectKey());
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        project.setArchived(false);

        projectRepository.save(project);

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setUser(user);
        projectMember.setRole(ProjectRole.PROJECT_MANAGER);
        projectMemberRepository.save(projectMember);

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setId(project.getId());
        projectResponseDTO.setName(project.getName());
        projectResponseDTO.setOnwerName(user.getUsername());
        projectResponseDTO.setCreatedAt(project.getCreatedAt());
        System.out.println("errorrrrr");

        notificationService.sendNotification(user.getId(), new NotificationSendDTO("Project "+ project.getName() + " created successfully!"));
        emailService.sendEmail(user.getEmail(), "Project Created", "Your project " + project.getName() + " has been created successfully!");
        System.out.println("Bugggggg");
        return projectResponseDTO;
    }

    public  ProjectDetailsDTO getProjectDetails(Long id) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get user from JWT
         String username = authentication.getName();
         User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new RuntimeException("User Not Found"));

    boolean isMember = projectMemberRepository
            .existsByProjectIdAndUserId(id, user.getId());

    if(!isMember){
        throw new RuntimeException("Access denied");
    }

      Project project =  projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project Not Found!"));

      return new ProjectDetailsDTO(
        project.getId(),
        project.getName(),
        project.getProjectKey(),
        project.getDescription(),
        project.getCreatedAt(),
        project.getUser().getUsername(),
        project.isArchived()

      );


        
    }

    public  String  updateProject(ProjectRequestDTO projectRequestDTO , Long id) {
        Project project = projectRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("No Project is Found!"));
        
        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        project.setProjectKey(projectRequestDTO.getProjectKey());
        projectRepository.save(project);

        return "Project Updated.";
    }

    public ProjectDetailsDTO  archiveProject(Long id) {

        Project project = projectRepository.findById(id).orElseThrow(()-> new RuntimeException("No Project Found."));
        project.setArchived(true);
        projectRepository.save(project);

         return new ProjectDetailsDTO(
        project.getId(),
        project.getName(),
        project.getProjectKey(),
        project.getDescription(),
        project.getCreatedAt(),
        project.getUser().getUsername(),
        project.isArchived()

      );
        
       
    }

    public  String addMemberToProject(Long id, AddMemberDTO addMemberDTO) {

        Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Project Not Found."));
        User user = userRepository.findById(addMemberDTO.getUserId())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
                     boolean exists = projectMemberRepository
            .existsByProjectIdAndUserId(id, addMemberDTO.getUserId());

    if (exists) {
        throw new RuntimeException("User already member of project");
    }
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
    ProjectRole projectRole;
    try {
        projectRole = ProjectRole.valueOf(addMemberDTO.getRole().toUpperCase());
    } catch (IllegalArgumentException e) {
        projectRole = ProjectRole.VIEWER;
    }
        projectMember.setRole(projectRole);
        projectMember.setUser(user);

        projectMemberRepository.save(projectMember);

        notificationService.sendNotification(user.getId(), new NotificationSendDTO("You have been added to project "+ project.getName() + " as " + projectRole.toString()));
        emailService.sendEmail(user.getEmail(), "Project Membership", "You have been added to project " + project.getName() + " as " + projectRole.toString());
        
        return "member added";
       

       
    }

    public  Set<ProjectMemberDTO> getProjectMembers(Long id) {
    Set< ProjectMember> projectMembers = projectMemberRepository.findByProjectId(id)
     .orElseThrow(() -> new RuntimeException("Project Not Found"));

     return projectMembers.stream()
                            .map(this::convertMemberDTO)
                            .collect(Collectors.toSet());
     

       
    }

    public Page<ProjectResponseDTO> getProjectsByPage(int page,int size , String sortBy){
                Pageable pageable = PageRequest.of(page, size,Sort.by(sortBy));
                 Page<Project> projects = projectRepository.findAll(pageable);
                 return projects.map(this::convertProjectsToDTO);   
                            
    }

    public ProjectMemberDTO convertMemberDTO(ProjectMember projectMember){
        return new ProjectMemberDTO(
            projectMember.getProject().getId(),
            projectMember.getUser().getId(),
            projectMember.getProject().getName(),
            projectMember.getUser().getUsername(),
            projectMember.getRole().toString()
        );
    }

    public void removeProjectMember(Long id, Long userId) {
        
    ProjectMember projectMember = projectMemberRepository
            .findByProjectIdAndUserId(id, userId)
            .orElseThrow(() -> new RuntimeException("Member not found"));

            projectMemberRepository.delete(projectMember);
            notificationService.sendNotification(userId, new NotificationSendDTO("You have been removed from project "+ projectMember.getProject().getName()));


    }

    public String updateMemberRole(Long id, Long userId, UpdateMemberDTO updateMemberDTO) {
            ProjectMember projectMember = projectMemberRepository.findByProjectIdAndUserId(id, userId)
            .orElseThrow(() -> new RuntimeException("Memeber Not Found"));
             ProjectRole projectRole;
        switch(updateMemberDTO.getRole().toLowerCase()){
            case "project_manager":
                projectRole = ProjectRole.PROJECT_MANAGER;
                break;
            case "developer" :
                projectRole=ProjectRole.DEVELOPER;
                break;
            default:
                projectRole = ProjectRole.VIEWER;
                break;
        }
            projectMember.setRole(projectRole);
            projectMemberRepository.save(projectMember);
            notificationService.sendNotification(userId,
                 new NotificationSendDTO("Your role in project "+ projectMember.getProject().getName() + " has been updated to " + projectRole.toString()));
            emailService.sendEmail(projectMember.getUser().getEmail(), "Project Role Updated", "Your role in project " + projectMember.getProject().getName() + " has been updated to " + projectRole.toString());
            return "Member Role Updated";
    }

    @PreAuthorize("hasRole('USER')")
    public  List<ProjectResponseDTO> getAllProjects() {
    //     User user = authServiceImpl.getCurrentUser();
    //     System.out.println(user.getRole()+"current user role ");
    //    if ("ADMIN".equals(user.getRole())){
    //     List<Project> projects = projectRepository.findAll();
    //     return projects.stream()
    //                 .map(this::convertProjectsToDTO)
    //                 .toList();
        
    // } 
        User user = authServiceImpl.getCurrentUser();
        
        List<Project> projects = projectRepository.findByUserId(user.getId())
        .orElseThrow(() -> new RuntimeException("No Projects Found for User")); // ✅ user sees only theirs
        return projects.stream()
                    .map(this::convertProjectsToDTO)
                    .toList();
    
       
    }
    public ProjectResponseDTO convertProjectsToDTO(Project project){
        return new ProjectResponseDTO(
            project.getId(),
            project.getName(),
            project.getUser().getUsername(),
            project.getCreatedAt()
        );
    }

    public  List<ProjectResponseDTO> getUserProjects() {
       User user = authServiceImpl.getCurrentUser();

        List<Project> projects = projectRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("No Projects Found for User"));

        return projects.stream()
            .map(this::convertProjectsToDTO)
            .toList();
        
    }

}
