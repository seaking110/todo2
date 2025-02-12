package com.example.todo.dto;

import com.example.member.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PageTodoResponseDto {
    private String title;
    private String content;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String memberName;

}
