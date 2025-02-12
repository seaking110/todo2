package com.example.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SaveMemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
