package com.example.global.util;

import com.example.domain.member.dto.MemberResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationUtil {
    public static void validateLogin(MemberResponseDto loginMember) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
    }
}
