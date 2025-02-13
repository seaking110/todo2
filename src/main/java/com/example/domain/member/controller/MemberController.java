package com.example.domain.member.controller;

import com.example.auth.service.AuthService;
import com.example.domain.member.dto.*;
import com.example.domain.member.entity.Member;
import com.example.domain.member.service.MemberService;
import com.example.global.util.Const;
import com.example.global.util.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
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


    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
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

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getMembers(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(memberService.getMembers(),HttpStatus.OK);
    }

    @PatchMapping("/members")
    public ResponseEntity<UpdateMemberResponseDto> updateMember(
            @RequestBody @Valid UpdateMemberRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(memberService.updateMember(loginMember.getId(), dto), HttpStatus.OK);
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember,
            HttpServletRequest request
    ) {
        ValidationUtil.validateLogin(loginMember);

        memberService.deleteMember(loginMember.getId());
        authService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
