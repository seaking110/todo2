package com.example.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaveTodoRequestDto {

    @NotBlank
    @Size(max = 12)
    private final String title;

    @NotBlank
    @Size(min = 10, max = 200)
    private final String content;
}
