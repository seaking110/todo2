package com.example.todo.entity;

import com.example.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    public Todo(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void updateTodo(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }
}
