package com.example.domain.todo.repository;


import com.example.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    boolean existsByMemberId(Long id);
}
