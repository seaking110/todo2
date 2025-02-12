package com.example.todo.service;

import com.example.member.dto.MemberResponseDto;
import com.example.member.entity.Member;
import com.example.member.service.MemberService;
import com.example.todo.dto.*;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private MemberService memberService;

    public TodoService(TodoRepository todoRepository, MemberService memberService) {
        this.todoRepository = todoRepository;
        this.memberService = memberService;
    }

    public SaveTodoResponseDto save(Long memberId, SaveTodoRequestDto dto) {
        Member member = memberService.getMemberById(memberId);
        Todo todo = todoRepository.save(
                new Todo(
                        member,
                        dto.getTitle(),
                        dto.getContent()
                )
        );
        return new SaveTodoResponseDto(
                todo.getId(),
                new MemberResponseDto(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getCreatedAt(),
                        member.getModifiedAt()
                ),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
    @Transactional(readOnly = true)
    public TodoResponseDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Member member = todo.getMember();
        MemberResponseDto memberDto = new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );
        return new TodoResponseDto(
                todo.getId(),
                memberDto,
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
    @Transactional(readOnly = true)
    public List<TodoResponseDto> getTodos() {
       List<Todo> list = todoRepository.findAll();
       List<TodoResponseDto> dtoList = new ArrayList<>();
       for (Todo todo : list) {
           Member member = todo.getMember();
           MemberResponseDto memberDto = new MemberResponseDto(
                   member.getId(),
                   member.getName(),
                   member.getEmail(),
                   member.getCreatedAt(),
                   member.getModifiedAt()
           );
           dtoList.add(
                   new TodoResponseDto(
                           todo.getId(),
                           memberDto,
                           todo.getTitle(),
                           todo.getContent(),
                           todo.getCreatedAt(),
                           todo.getModifiedAt()
                   )
           );
       }
        return dtoList;
    }


    public UpdateTodoResponseDto updateTodo(Long id, UpdateTodoRequestDto dto, MemberResponseDto loginMember) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Member member = todo.getMember();
        if(member.getId() != loginMember.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        todo.updateTodo(dto.getTitle(), dto.getContent());

        MemberResponseDto memberDto = new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getModifiedAt()
        );

        return new UpdateTodoResponseDto(
                todo.getId(),
                memberDto,
                todo.getTitle(),
                todo.getContent(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    public void deleteTodo(Long id, MemberResponseDto loginMember) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(todo.getMember().getId() != loginMember.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        todoRepository.deleteById(id);
    }
}
