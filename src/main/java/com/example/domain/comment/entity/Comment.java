package com.example.domain.comment.entity;

import com.example.domain.member.entity.Member;
import com.example.global.entity.BaseEntity;
import com.example.domain.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(String content, Todo todo, Member member) {
        this.content = content;
        this.todo = todo;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public void update(String comment) {
        this.content = comment;
    }
}
