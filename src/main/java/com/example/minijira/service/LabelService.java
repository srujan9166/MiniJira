package com.example.minijira.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.example.minijira.dto.labelDTO.LabelCreateDTO;
import com.example.minijira.dto.labelDTO.LabelResponseDTO;
import com.example.minijira.model.Label;
import com.example.minijira.model.Project;
import com.example.minijira.repository.LabelRepository;
import com.example.minijira.repository.ProjectRepository;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;
    public LabelService(LabelRepository labelRepository, ProjectRepository projectRepository) {
        this.labelRepository = labelRepository;
        this.projectRepository = projectRepository;
    }
    public  String createLabel(LabelCreateDTO labelCreateDTO) {

        Project project = projectRepository.findById(labelCreateDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Label label = new Label();
        label.setName(labelCreateDTO.getName());
        label.setColor(labelCreateDTO.getColor());  
        label.setProject(project);
        labelRepository.save(label);
        return "Label created successfully with name " + label.getName() + " and color " + label.getColor() + " for project " + project.getName();
        
    }
    public LabelResponseDTO getLabelById(Long id) {
        return labelRepository.findById(id)
                .map(this::mapToLabelResponseDTO)
                .orElseThrow(() -> new RuntimeException("Label not found"));
    }

    public LabelResponseDTO mapToLabelResponseDTO(Label label) {
        return LabelResponseDTO.builder()
                .id(label.getId())
                .name(label.getName())
                .color(label.getColor())
                .projectName(label.getProject().getName())
                .build();
    }
    public void deleteLabelById(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new RuntimeException("Label not found");
        }
        labelRepository.deleteById(id);
    }


}
