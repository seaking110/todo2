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