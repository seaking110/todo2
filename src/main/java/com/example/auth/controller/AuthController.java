package com.example.auth.controller;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;
import com.example.auth.service.AuthService;
import com.example.domain.member.dto.SaveMemberRequestDto;
import com.example.domain.member.dto.SaveMemberResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SaveMemberResponseDto> createMember(@RequestBody @Valid SaveMemberRequestDto dto) {
        return new ResponseEntity<>(authService.save(dto), HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto dto,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(authService.login(dto, request),HttpStatus.OK);
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
