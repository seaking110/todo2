package com.example.comment.service;

import com.example.comment.dto.*;
import com.example.comment.entity.Comment;
import com.example.comment.repository.CommentRepository;
import com.example.member.dto.MemberResponseDto;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final TodoService todoService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    public CommentService(TodoService todoService, MemberService memberService, CommentRepository commentRepository) {
        this.todoService = todoService;
        this.memberService = memberService;
        this.commentRepository = commentRepository;
    }

    public SaveCommentResponseDto save(SaveCommentRequestDto dto, Long memberId, Long todoId) {
        Member member = memberService.getMemberById(memberId);

        Todo todo = todoService.getTodoById(todoId);
        Comment comment = commentRepository.save(new Comment(dto.getComment(), todo, member));


        return new SaveCommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getTodo().getId(),
                comment.getMember().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getTodo().getId(),
                comment.getMember().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments() {
        List<Comment> list = commentRepository.findAll();
        List<CommentResponseDto> resultList = new ArrayList<>();
        for (Comment comment : list) {
            resultList.add(
                    new CommentResponseDto(
                            comment.getId(),
                            comment.getContent(),
                            comment.getTodo().getId(),
                            comment.getMember().getId(),
                            comment.getCreatedAt(),
                            comment.getModifiedAt()
                    )
            );
        }
        return resultList;
    }


    public UpdateCommentResponseDto updateCommentById(Long id, UpdateCommentRequestDto dto, Long memberId) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!comment.getMember().getId().equals(memberId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"본인이 쓴 댓글만 수정 가능합니다!");
        }
        comment.update(dto.getComment());
        return new UpdateCommentResponseDto(
                id,
                comment.getContent(),
                comment.getTodo().getId(),
                memberId,
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    public void deleteCommentById(Long id, MemberResponseDto loginMember) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!comment.getMember().getId().equals(loginMember.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 쓴 댓글만 삭제 가능합니다!");
        }
        commentRepository.deleteById(id);
    }
}
