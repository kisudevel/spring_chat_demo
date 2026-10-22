-- 회원 샘플 데이터
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (11, 'admin@test.com', '$2b$10$nzU9bBvv/yq2QXdckCQ6z.Wys7gNrG8xbBuRd/B3q7Hsyw7CRc8ue', '관리자', 'ADMIN',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (1, 'kim@test.com', '$2b$10$62ySQSvLH0aW1YG6OJoky.ZY1Y5oDuDmlSbrFsFu5x9g0D4ou.tJ6', '김코딩', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (2, 'lee@test.com', '$2b$10$jnYeSJR78QfnKWm45QmFE.WrGygyZofxCDXwvlKL66p5ZBQqCMn3K', '이자바', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (3, 'park@test.com', '$2b$10$DccC8sSIb4N.OR6gJx9l/ubXTFSzhRdxuhRe207s3uph.s9zUhfqa', '박스프링', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (4, 'choi@test.com', '$2b$10$UFGt.ekyA2EceU8nc.WchOnMnqZDo4PgYh85liBLqTiighjpoh9PW', '최백엔드', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (5, 'jung@test.com', '$2b$10$BRvMtVJdn9lcezZsOfUVG.8FgPtmlqcI1TBAfxGjgpSQbJcGwiIVu', '정디비', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (6, 'kang@test.com', '$2b$10$BGNAJMGJO7XWsxvkquQmRux2c4s/a1vVK1i402ELzREvbHhSh0uMC', '강리액트', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (7, 'yoon@test.com', '$2b$10$TIz3ZcjyKl7ehJACS2mq4.mR11iyr/lEHXM1tMQK/xXWb20N0DWei', '윤파이썬', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (8, 'lim@test.com', '$2b$10$VXSi1TdrffsblzDyPFGJV.AqiV/NkdJSB6icu7wlSwTgk/CbpWlmm', '임데브옵스', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (9, 'han@test.com', '$2b$10$Rgf5Hf3UtE8I5e/aZS/yeuIurkzfIktNL2.EqM1VrmL.X6878.3IO', '한알고', 'USER',
        CURRENT_TIMESTAMP);
INSERT INTO member (id, email, password, nickname, role, created_at)
VALUES (10, 'oh@test.com', '$2b$10$9tniXIt9pNc6i2Km9Sc9xe84l8Fhkf/GVHLaBPdee7dkHAJsKmnpy', '오퍼블', 'USER',
        CURRENT_TIMESTAMP);
ALTER TABLE MEMBER ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM MEMBER);
