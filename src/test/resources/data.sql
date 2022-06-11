INSERT INTO GROUP_TB
VALUES (1, 'mon0516', '민지네', '취준생 민지와 직딩 친구들과 함께 진행하는 프로젝트..^_^');

INSERT INTO USER_TB
VALUES (1, 'm04j00@gmail.com', '1.png', 'Min J', '취준생', 'doremisolsol', 1);
INSERT INTO USER_TB
VALUES (2, 'akwls@gmail.com', '2.png', '마진', '테크타카 1', 'doremisolsol', 1);
INSERT INTO USER_TB
VALUES (3, 'srin@gmail.com', '3.png', '세라', '테크타카 2', 'doremisolsol', 1);
INSERT INTO USER_TB
VALUES (4, 'sky@gmail.com', '4.png', 'SKY', '비지피웍스', 'doremisolsol', 1);
INSERT INTO USER_TB
VALUES (5, 'ywoo@gmail.com', '5.png', '가연어', '비거라지', 'doremisolsol', 1);

INSERT INTO INGREDIENT_TB
VALUES (1, NOW(), NOW(), 'VEGGIE', '3', '2024-02-02', '1.PNG', '햇감자 구워먹기', '햇감자', NOW(), 'ROOM_TEMP', 1);
INSERT INTO INGREDIENT_TB
VALUES (2, NOW(), NOW(), 'VEGGIE', '5', '2024-07-02', '2.PNG', '고구마 구워먹기', '왕고구마', NOW(), 'FRIDGE', 1);

INSERT INTO QUEST_TB
VALUES (1, NOW(), NOW(), 3, TRUE, '오늘 저녁은 치킨이었으면...', '치킨 사서 올 사람', 1, 1);
INSERT INTO QUEST_TB
VALUES (2, NOW(), NOW(), -1, FALSE, '집에 콜라가 없어...', '콜라 사서 올 사람', 1, 1);

INSERT INTO CHORE_TB
VALUES (1, NOW(), NOW(), 'COOK', 'BEFORE', NOW(), '하늘이 요리하는 날', 1, 4);
INSERT INTO CHORE_TB
VALUES (2, NOW(), NOW(), 'COOK', 'SUCCESS', NOW(), '하늘이 요리하는 날', 1, 4);
INSERT INTO CHORE_TB
VALUES (3, NOW(), NOW(), 'COOK', 'SUCCESS', NOW(), '하늘이 요리하는 날', 1, 4);
INSERT INTO CHORE_TB
VALUES (4, NOW(), NOW(), 'COOK', 'SUCCESS', NOW(), '하진이 김볶하는 날', 1, 2);
INSERT INTO CHORE_TB
VALUES (5, NOW(), NOW(), 'COOK', 'SUCCESS', NOW(), '하진이 김볶하는 날', 1, 2);

INSERT INTO CHORE_TB
VALUES (6, NOW(), NOW(), 'SHOPPING', 'SUCCESS', NOW(), '연우가 연어 사는 날', 1, 5);
INSERT INTO CHORE_TB
VALUES (7, NOW(), NOW(), 'SHOPPING', 'SUCCESS', NOW(), '연우가 집 사는 날', 1, 5);
INSERT INTO CHORE_TB
VALUES (8, NOW(), NOW(), 'SHOPPING', 'SUCCESS', NOW(), '세린이가 뿌링클 사는 날', 1, 3);

INSERT INTO CHORE_TB
VALUES (9, NOW(), NOW(), 'DISH_WASHING', 'SUCCESS', NOW(), '취준생 민지가 설거지 하는 날', 1, 1);
INSERT INTO CHORE_TB
VALUES (10, NOW(), NOW(), 'DISH_WASHING', 'SUCCESS', NOW(), '취준생 민지가 설거지 하는 날', 1, 1);
INSERT INTO CHORE_TB
VALUES (11, NOW(), NOW(), 'DISH_WASHING', 'SUCCESS', NOW(), '취준생 민지가 설거지 하는 날', 1, 1);

INSERT INTO USER_TB (user_id, user_email, user_image_path, user_name, user_nickname, user_password)
VALUES (6, 'freemily@gmail.com', '6.png', '프리밀리', '아이티쇼', '$2a$10$YtRPNN74FuaabDZ6XH6xpuF/X3.tpQbHRaLb02XYhtMmT1swYyPne');

INSERT INTO USER_TB
VALUES (7, 'family@gmail.com', '7.png', '가족', '가족공유', '$2a$10$YtRPNN74FuaabDZ6XH6xpuF/X3.tpQbHRaLb02XYhtMmT1swYyPne', 1);

INSERT INTO GROUP_TB(group_id, group_invite_code, group_name)
VALUES (2, 'mon0516', '민지네');
