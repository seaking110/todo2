package com.example.todo.dto;

import com.example.member.dto.MemberResponseDto;
import com.example.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SaveTodoResponseDto {
    private Long id;
    private MemberResponseDto memberResponseDto;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
