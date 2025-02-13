# 프로젝트: 일정 관리 시스템 (JPA 활용)

## 소개
본 프로젝트는 JPA를 활용하여 백엔드로만 구현된 일정 관리 시스템입니다.
뷰는 제공되지 않으며, API 테스트는 Postman을 이용하여 확인해야 합니다.

---
기술 스택:

Spring Boot
JPA(Hibernate)
MySQL
Authentication: Session & Cookie 기반 인증

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
| 회원가입   | POST       | /api/members      | email, name, password | 성공 메시지 |
| 로그인     | POST       | /api/login        | email, password | 세션 정보 |
| 로그아웃   | POST       | /api/logout       | 없음       | 성공 메시지 |

### 🏷️ 멤버 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 멤버 생성  | POST       | /api/members      | email, name, password | 생성된 멤버 |
| 멤버 단일 조회 | GET        | /api/members/{id} | 없음       | 멤버 정보 |
| 멤버 전체 조회 | GET        | /api/members      | 없음       | 멤버 리스트 |
| 멤버 수정  | PUT        | /api/members/{id} | name, password | 수정된 멤버 정보 |
| 멤버 삭제  | DELETE     | /api/members/{id} | 없음       | 성공 메시지 |

### 🏷️ 할일 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 할일 생성  | POST       | /api/todos        | title, content, member_id | 생성된 할일 |
| 할일 단일 조회 | GET        | /api/todos/{id}   | 없음       | 할일 정보 |
| 할일 전체 조회 | GET        | /api/todos       | 없음       | 할일 리스트 |
| 할일 수정  | PUT        | /api/todos/{id}   | title, content | 수정된 할일 |
| 할일 삭제  | DELETE     | /api/todos/{id}   | 없음       | 성공 메시지 |

### 🏷️ 댓글 관련 API
| 기능       | HTTP Method | URL                | 요청 데이터 | 응답 데이터 |
|------------|------------|--------------------|------------|------------|
| 댓글 생성  | POST       | /api/comments     | content, member_id, todo_id | 생성된 댓글 |
| 댓글 단일 조회 | GET        | /api/comments/{id}| 없음       | 댓글 정보 |
| 댓글 전체 조회 | GET        | /api/comments     | 없음       | 댓글 리스트 |
| 댓글 수정  | PUT        | /api/comments/{id}| content    | 수정된 댓글 |
| 댓글 삭제  | DELETE     | /api/comments/{id}| 없음       | 성공 메시지 |

