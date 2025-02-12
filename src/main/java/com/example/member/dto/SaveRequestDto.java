package com.example.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveRequestDto {
    @NotBlank
    private final String name;
    @Email
    private final String email;
    @NotBlank
    private final String password;

}
