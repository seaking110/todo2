package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTodoRequestDto {
    @NotBlank
    private final String title;
    @NotBlank
    private final String content;
}
