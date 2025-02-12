package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.example.todo", "com.example.member", "com.example.comment"})
@EntityScan(basePackages = { "com.example.todo.entity", "com.example.member.entity", "com.example.comment.entity" })
@EnableJpaRepositories(basePackages = {"com.example.todo.repository", "com.example.member.repository", "com.example.comment.repository"})
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

}
