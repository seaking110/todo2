package com.example.todo.dto;

import com.example.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaveTodoRequestDto {
    @NotBlank
    private final String title;
    @NotBlank
    private final String content;

}
