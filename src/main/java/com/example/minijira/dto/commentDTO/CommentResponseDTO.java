package com.example.minijira.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDTO {

    private Long id;
    private String content;
    private String authorUsername;
    private Long issueId;

}
