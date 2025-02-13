package com.example.domain.todo.service;

import com.example.domain.member.dto.MemberResponseDto;
import com.example.domain.member.entity.Member;
import com.example.domain.member.service.MemberService;
import com.example.domain.todo.dto.*;
import com.example.domain.todo.entity.Todo;
import com.example.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberService memberService;

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
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 페이징을 이용한 전체 일정 조회
    @Transactional(readOnly = true)
    public Page<PageTodoResponseDto> getTodos(int page, int size) {
       Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Order.desc("modifiedAt")));
        return todoRepository.findAll(pageable)
                .map(todo -> new PageTodoResponseDto(
                        todo.getTitle(),
                        todo.getContent(),
                        todo.getComments().size(),
                        todo.getCreatedAt(),
                        todo.getModifiedAt(),
                        todo.getMember().getName()
                ));
    }

    public UpdateTodoResponseDto updateTodo(Long id, UpdateTodoRequestDto dto, MemberResponseDto loginMember) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Member member = todo.getMember();
        // 댓글을 쓴 멤버와 현재 로그인한 멤버가 같은지 검증
        if(member.getId() != loginMember.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        todo.updateTodo(dto.getTitle(), dto.getContent());

        return new UpdateTodoResponseDto(
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

    public void deleteTodo(Long id, MemberResponseDto loginMember) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 댓글을 쓴 멤버와 현재 로그인한 멤버가 같은지 검증
        if(todo.getMember().getId() != loginMember.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        todoRepository.deleteById(id);
    }
}
