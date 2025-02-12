package com.example.todo.controller;

import com.example.member.dto.MemberResponseDto;
import com.example.member.session.Const;
import com.example.todo.dto.*;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<SaveTodoResponseDto> saveTodo(
            @RequestBody @Valid SaveTodoRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(todoService.save(loginMember.getId(), dto),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getTodos(
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

    @PatchMapping({"/{id}"})
    public ResponseEntity<UpdateTodoResponseDto> updateTodoById(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(todoService.updateTodo(id, dto, loginMember),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_MEMBER, required = false) MemberResponseDto loginMember
    ) {
        if (loginMember == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        todoService.deleteTodo(id, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
