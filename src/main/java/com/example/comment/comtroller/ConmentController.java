package com.example.comment.comtroller;

import com.example.comment.dto.*;
import com.example.comment.service.CommentService;
import com.example.member.dto.MemberResponseDto;
import com.example.member.session.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ConmentController {
    private final CommentService commentService;

    public ConmentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{todoId}/comments")
    public ResponseEntity<SaveCommentResponseDto> saveComment(
            @RequestBody SaveCommentRequestDto dto,
            @PathVariable Long todoId,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        validateLogin(loginMember);
        return new ResponseEntity<>(commentService.save(dto, loginMember.getId(), todoId), HttpStatus.CREATED);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        validateLogin(loginMember);
        return new ResponseEntity<>(commentService.getCommentById(id),HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        validateLogin(loginMember);
        return new ResponseEntity<>(commentService.getComments(),HttpStatus.OK);
    }

    @PatchMapping("/coments/{id}")
    public ResponseEntity<UpdateCommentResponseDto> updateCommentById(
            @PathVariable Long id,
            @RequestBody UpdateCommentRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        validateLogin(loginMember);
        return new ResponseEntity<>(commentService.updateCommentById(id, dto, loginMember.getId()),HttpStatus.OK);
    }

    @DeleteMapping("/coments/{id}")
    public ResponseEntity<Void> deleteCommentById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        validateLogin(loginMember);
        commentService.deleteCommentById(id, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateLogin(MemberResponseDto loginMember) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
    }


}
