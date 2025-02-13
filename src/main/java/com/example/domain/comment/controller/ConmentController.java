package com.example.domain.comment.controller;

import com.example.domain.comment.dto.*;
import com.example.domain.comment.service.CommentService;
import com.example.domain.member.dto.MemberResponseDto;
import com.example.global.util.Const;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class ConmentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/{todoId}")
    public ResponseEntity<SaveCommentResponseDto> createComment(
            @RequestBody @Valid SaveCommentRequestDto dto,
            @PathVariable Long todoId,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        return new ResponseEntity<>(commentService.save(dto, loginMember.getId(), todoId), HttpStatus.CREATED);
    }

    // 댓글 단일 검색
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getCommentById(id),HttpStatus.OK);
    }

    // 댓글 전체 검색
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        return new ResponseEntity<>(commentService.getComments(),HttpStatus.OK);
    }

    // 댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateCommentResponseDto> updateCommentById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCommentRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        return new ResponseEntity<>(commentService.updateCommentById(id, dto, loginMember.getId()),HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        commentService.deleteCommentById(id, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
