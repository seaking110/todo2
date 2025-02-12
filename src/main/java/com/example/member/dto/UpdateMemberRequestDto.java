package com.example.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UpdateMemberRequestDto {
    private final String name;
    @Email
    private final String email;
    @NotBlank
    private final String oldPassword;

    private final String newPassword;
}
