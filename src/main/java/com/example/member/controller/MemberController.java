package com.example.member.controller;

import com.example.member.dto.*;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import com.example.member.session.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SaveMemberResponseDto> saveMember(@RequestBody @Valid SaveMemberRequestDto dto) {
        return new ResponseEntity<>(memberService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> getMemberById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
        if (loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(memberService.getMembers(),HttpStatus.OK);
    }

    @PatchMapping("/members")
    public ResponseEntity<UpdateMemberResponseDto> updateMember(
            @RequestBody @Valid UpdateMemberRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(memberService.updateMember(loginMember.getId(), dto), HttpStatus.OK);
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember,
            HttpServletRequest request
    ) {
        if (loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        memberService.deleteMember(loginMember.getId());

        return logout(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto dto,
            HttpServletRequest request
    ) {
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

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // 세션이 존재하면 -> 로그인이 된 경우
        if(session != null) {
            session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }}
