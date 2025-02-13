# 프로젝트: 일정 관리 시스템 (JPA 활용)

## 소개
본 프로젝트는 JPA를 활용하여 백엔드로만 구현된 일정 관리 시스템입니다.
뷰는 제공되지 않으며, API 테스트는 Postman을 이용하여 확인해야 합니다.

---
## 기술 스택

- Spring Boot
- JPA(Hibernate)
- MySQL
- Authentication: Session & Cookie 기반 인증

---

## 주요 기능
- 회원가입
- 로그인 / 로그아웃
- 멤버 단일 조회, 전체 조회, 수정, 삭제
- 할일 생성, 단일 조회, 전체 조회, 수정, 삭제
- 댓글 생성, 단일 조회, 전체 조회, 수정, 삭제
- 필터 기능으로 로그인 하지 않은 사용자의 접근 제한
- 비밀번호 암호화 복호화 기능을 통해 보안 강화
- 할일 전체 조회에 페이징 기능 구현

---

## 📌 ERD 구조
![Image](https://github.com/user-attachments/assets/4039485d-6417-4e0a-a83c-40fcd1e2ee6c)

---

## 📄 테이블 생성 SQL
```sql
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    todo_id BIGINT NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (todo_id) REFERENCES todo(id)
);
```

---

## 📌 API 명세서

### 🏷️ 회원 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 회원가입   | POST       | /auth/members      | name, email, password | 201 created, id, name, email, createdAt, modifiedAt |
| 로그인     | POST       | /auth/login        | email, password | 200 ok, id |
| 로그아웃   | GET       | /auth/logout       | X       | 200 ok |

### 🏷️ 멤버 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 멤버 단일 조회 | GET        | /members/{id} | X       | 200 ok, id, name, email, createdAt, modifiedAt |
| 멤버 전체 조회 | GET        | /members      | X       | 200 ok, List<id, name, email, createdAt, modifiedAt> |
| 멤버 수정  | PATCH        | /members | name, password | 200 ok, id, name, email, createdAt, modifiedAt |
| 멤버 삭제  | DELETE     | /members | X       | 200 ok |

### 🏷️ 할일 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 할일 생성  | POST       | /todos        | title, content | 201 created, id, member 정보, title, content, createdAt, modifiedAt |
| 할일 단일 조회 | GET        | /todos/{id}   | X       | 200 ok, id, member 정보, title, content, createdAt, modifiedAt |
| 할일 전체 조회 | GET        | /todos?page=1&size=10      | X       | 200 ok, List<id, member 정보, title, content, createdAt, modifiedAt>  |
| 할일 수정  | PATCH        | /todos/{id}   | title, content | 200 ok, id, member 정보, title, content, createdAt, modifiedAt |
| 할일 삭제  | DELETE     | /todos/{id}   | X       | 200 ok |

### 🏷️ 댓글 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 댓글 생성  | POST       | /comments     | content | 201 created, id, comment, todoId, memberId, createdAt, modifiedAt |
| 댓글 단일 조회 | GET        | /comments/{todoId}| X       | 200 ok, id, comment, todoId, memberId, createdAt, modifiedAt |
| 댓글 전체 조회 | GET        | /comments     | X       |  | 200 ok, List<id, comment, todoId, memberId, createdAt, modifiedAt> |
| 댓글 수정  | PATCH        | /comments/{todoId}| content    | 200 ok, id, comment, todoId, memberId, createdAt, modifiedAt |
| 댓글 삭제  | DELETE     | /comments/{todoId}| X       | 200 ok |

