package com.example.member.service;

import com.example.member.dto.*;
import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import com.example.todo.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;



    public SaveMemberResponseDto save(SaveMemberRequestDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = memberRepository.save(new Member(dto.getName(), dto.getEmail(), encodedPassword));
        return new SaveMemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return new LoginResponseDto(member.getId());
    }
    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers() {
        List<Member> list = memberRepository.findAll();
        List<MemberResponseDto> dtoList = new ArrayList<>();
        for (Member member : list) {
            dtoList.add(
                    new MemberResponseDto(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getCreatedAt(),
                        member.getModifiedAt()
                    )
            );
        }
        return dtoList;
    }


    public UpdateMemberResponseDto updateMember(Long id, UpdateMemberRequestDto dto) {
        // 멤버 조회
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Member not found"));
        // 비밀번호 검증
        if (!passwordEncoder.matches(dto.getOldPassword(), member.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (dto.getName() != null && !dto.getName().isBlank()) {
            member.updateName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            member.updateEmail(dto.getEmail());
        }
        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
            member.updatePassword(encodedPassword);
        }
        return new UpdateMemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
    }

    public void deleteMember(Long id) {
        // 멤버 조회
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Member not found"));
        memberRepository.deleteById(id);
    }
}
