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

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;


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
        // 세션이 존재하면 -> 로그인이 된 경우
        if(session != null) {
            session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        }
    }

    public SaveMemberResponseDto save(SaveMemberRequestDto dto) {
        return memberService.save(dto);
    }
}
