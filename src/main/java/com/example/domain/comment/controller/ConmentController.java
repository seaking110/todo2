package com.example.domain.comment.controller;

import com.example.domain.comment.dto.*;
import com.example.domain.comment.service.CommentService;
import com.example.domain.member.dto.MemberResponseDto;
import com.example.global.util.Const;
import com.example.global.util.ValidationUtil;
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

    @PostMapping("/{todoId}")
    public ResponseEntity<SaveCommentResponseDto> createComment(
            @RequestBody @Valid SaveCommentRequestDto dto,
            @PathVariable Long todoId,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(commentService.save(dto, loginMember.getId(), todoId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(commentService.getCommentById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(commentService.getComments(),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateCommentResponseDto> updateCommentById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCommentRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(commentService.updateCommentById(id, dto, loginMember.getId()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        commentService.deleteCommentById(id, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
