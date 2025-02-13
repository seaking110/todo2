package com.example.domain.todo.controller;

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
import com.example.global.util.ValidationUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;


    @PostMapping
    public ResponseEntity<SaveTodoResponseDto> createTodo(
            @RequestBody @Valid SaveTodoRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(todoService.save(loginMember.getId(), dto),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        Todo todo = todoService.getTodoById(id);
        Member member = todo.getMember();
        MemberResponseDto memberDto = new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
        return new ResponseEntity<>(new TodoResponseDto(
                todo.getId(),
                memberDto,
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PageTodoResponseDto>> getTodos(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        return new ResponseEntity<>(todoService.getTodos(page, size), HttpStatus.OK);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<UpdateTodoResponseDto> updateTodoById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTodoRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);

        return new ResponseEntity<>(todoService.updateTodo(id, dto, loginMember),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        ValidationUtil.validateLogin(loginMember);
        todoService.deleteTodo(id, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
