package com.example.comment.entity;

import com.example.member.entity.Member;
import com.example.todo.entity.BaseEntity;
import com.example.todo.entity.Todo;
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
    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne
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
