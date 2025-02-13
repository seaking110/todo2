package com.example.domain.todo.controller;

import com.example.domain.comment.dto.CommentResponseDto;
import com.example.domain.comment.entity.Comment;
import com.example.domain.member.dto.MemberResponseDto;
import com.example.domain.member.entity.Member;
import com.example.global.util.Const;
import com.example.domain.todo.dto.*;
import com.example.domain.todo.entity.Todo;
import com.example.domain.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<SaveTodoResponseDto> createTodo(
            @RequestBody @Valid SaveTodoRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        return new ResponseEntity<>(todoService.save(loginMember.getId(), dto),HttpStatus.CREATED);
    }

    // 단일 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        Member member = todo.getMember();
        MemberResponseDto memberDto = new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
        List<CommentResponseDto> commentDtoList = new ArrayList<>();
        for (Comment comment : todo.getComments()){
            commentDtoList.add(
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
        return new ResponseEntity<>(new TodoResponseDto(
                todo.getId(),
                memberDto,
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt(),
                commentDtoList
        ), HttpStatus.OK);
    }

    // 페이징을 이용한 일정 조회
    @GetMapping
    public ResponseEntity<Page<PageTodoResponseDto>> getTodos(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(todoService.getTodos(page, size), HttpStatus.OK);
    }

    // 일정 수정
    @PatchMapping({"/{id}"})
    public ResponseEntity<UpdateTodoResponseDto> updateTodoById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTodoRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        return new ResponseEntity<>(todoService.updateTodo(id, dto, loginMember),HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        todoService.deleteTodo(id, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
