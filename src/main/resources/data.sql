-- INSERT INTO member (id, name) VALUES (1, '이름1');
-- INSERT INTO member (id, name) VALUES (2, '이름2');
-- INSERT INTO member (id, name) VALUES (3, '이름3');

-- INSERT INTO article (title, content) VALUES ('제목1', '내용1');
-- INSERT INTO article (title, content) VALUES ('제목2', '내용2');
-- INSERT INTO article (title, content) VALUES ('제목3', '내용3');

-- INSERT INTO article (title, content, created_at, updated_at) VALUES ('제목 1', '내용 1', NOW(), NOW());
-- INSERT INTO article (title, content, created_at, updated_at) VALUES ('제목 2', '내용 2', NOW(), NOW());
-- INSERT INTO article (title, content, created_at, updated_at) VALUES ('제목 3', '내용 3', NOW(), NOW());

INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('제목1', '내용1', 'user1', NOW(), NOW());
INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('제목2', '내용2', 'user2', NOW(), NOW());
INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('제목3', '내용3', 'user3', NOW(), NOW());