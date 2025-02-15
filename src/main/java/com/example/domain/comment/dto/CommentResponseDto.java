package com.example.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;

    private String comment;

    private Long todoId;

    private Long memberId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
