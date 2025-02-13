package com.example.domain.todo.dto;

import com.example.domain.comment.dto.CommentResponseDto;
import com.example.domain.comment.entity.Comment;
import com.example.domain.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long id;

    private MemberResponseDto memberResponseDto;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentsList;
}
