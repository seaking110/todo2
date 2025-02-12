package com.example.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveMemberRequestDto {
    @NotBlank
    private final String name;
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;

}
