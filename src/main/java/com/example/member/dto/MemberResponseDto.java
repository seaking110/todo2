package com.example.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import netscape.javascript.JSObject;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
