package com.example.domain.member.controller;

import com.example.auth.service.AuthService;
import com.example.domain.member.dto.*;
import com.example.domain.member.entity.Member;
import com.example.domain.member.service.MemberService;
import com.example.global.util.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    // 단일 멤버 조회
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);

        return new ResponseEntity<>(
                new MemberResponseDto(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getCreatedAt(),
                        member.getModifiedAt()
                ), HttpStatus.OK);
    }

    // 전체 멤버 조회
    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        return new ResponseEntity<>(memberService.getMembers(),HttpStatus.OK);
    }

    // 로그인한 멤버 수정
    @PatchMapping("/members")
    public ResponseEntity<UpdateMemberResponseDto> updateMember(
            @RequestBody @Valid UpdateMemberRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        return new ResponseEntity<>(memberService.updateMember(loginMember.getId(), dto), HttpStatus.OK);
    }

    // 로그인한 멤버 삭제 후 로그아웃
    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember,
            HttpServletRequest request
    ) {
        memberService.deleteMember(loginMember.getId());
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
