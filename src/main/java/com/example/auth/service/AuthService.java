package com.example.auth.service;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;
import com.example.domain.member.dto.MemberResponseDto;
import com.example.domain.member.dto.SaveMemberRequestDto;
import com.example.domain.member.dto.SaveMemberResponseDto;
import com.example.domain.member.entity.Member;
import com.example.domain.member.service.MemberService;
import com.example.global.util.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final MemberService memberService;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request) {
        LoginResponseDto responseDto = memberService.login(dto);
        HttpSession session = request.getSession();
        Member member = memberService.getMemberById(responseDto.getId());
        MemberResponseDto loginMember = new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
        session.setAttribute(Const.LOGIN_MEMBER, loginMember);
        return responseDto;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }

    @Transactional
    public SaveMemberResponseDto save(SaveMemberRequestDto dto) {
        return memberService.save(dto);
    }
}
