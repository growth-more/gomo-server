drop table if exists member;
drop table if exists survey_question;
drop table if exists survey_item;
drop table if exists survey_result;
drop table if exists interest;
drop table if exists interest_relation;
drop table if exists major_interest;
drop table if exists score_threshold_policy;
drop table if exists streak;
drop table if exists repeat_quest;
drop table if exists assign_quest;
drop table if exists quest_score_policy;
drop table if exists quest_point_policy;
drop table if exists point;
drop table if exists point_wallet;
drop table if exists global_variables;

CREATE TABLE member (
    id binary(16) NOT NULL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    profile_image_url VARCHAR(512),
    handle VARCHAR(50) UNIQUE,
    name VARCHAR(30),
    motto VARCHAR(255),
    daily_quest_threshold TINYINT,
    weekly_quest_threshold TINYINT,
    monthly_quest_threshold TINYINT,
    login_provider ENUM('EMAIL', 'GOOGLE'),
    role_type ENUM('ROLE_MEMBER', 'ROLE_ADMIN'),
    subscription_plan ENUM('FREE', 'BASIC', 'PREMIUM'),
    activate_status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED', 'DELETED'),
    sign_up_date_time DATETIME(6),
    last_login_date_time DATETIME(6),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255),
    deleted_at DATETIME(6)
);

CREATE TABLE survey_question (
    id binary(16) NOT NULL PRIMARY KEY,
    question_select_type VARCHAR(50),
    is_required TINYINT(1),
    content VARCHAR(255),
    display_order INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE survey_item (
    id binary(16) NOT NULL PRIMARY KEY,
    survey_question_id binary(16),
    content VARCHAR(255),
    display_order INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE survey_result (
    respondent_id binary(16),
    survey_question_id binary(16),
    survey_item_id binary(16),
    survey_item_content varchar(255),
    custom_answer varchar(255)
);

CREATE TABLE interest (
    id binary(16) NOT NULL PRIMARY KEY,
    registrant_id binary(16),
    level INT,
    score INT,
    score_threshold INT,
    total_score INT,
    name VARCHAR(30),
    logo_url VARCHAR(512),
    version BIGINT DEFAULT 0 NOT NULL,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE interest_relation (
    id binary(16) NOT NULL PRIMARY KEY,
    registrant_id binary(16),
    parent_interest_id binary(16),
    child_interest_id binary(16),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE major_interest (
    id binary(16) NOT NULL PRIMARY KEY,
    registrant_id binary(16),
    interest_id binary(16),
    display_order INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE score_threshold_policy (
    level SMALLINT,
    threshold INT
);

CREATE TABLE streak (
    id binary(16) NOT NULL PRIMARY KEY,
    achiever_id binary(16),
    streak_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    filled_date DATE,
    completed_quest_count TINYINT,
    version BIGINT DEFAULT 0 NOT NULL,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE repeat_quest (
    id binary(16) NOT NULL PRIMARY KEY,
    participant_id binary(16),
    subject_id binary(16),
    subject_name VARCHAR(30),
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    content VARCHAR(255),
    display_order INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE assign_quest (
    id binary(16) NOT NULL PRIMARY KEY,
    participant_id binary(16),
    subject_id binary(16),
    subject_name VARCHAR(30),
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    content VARCHAR(255),
    proof VARCHAR(512),
    is_confirmed TINYINT(1),
    is_completed TINYINT(1),
    display_order INT,
    start_date_time DATETIME(6),
    completed_date_time DATETIME(6),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE quest_score_policy (
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    score INT
);

CREATE TABLE quest_point_policy (
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    points INT
);

CREATE TABLE point (
    id binary(16) NOT NULL PRIMARY KEY,
    transactor_id binary(16),
    source_type ENUM('QUEST', 'ATTENDANCE', 'STORE', 'EVENT'),
    transaction_type ENUM('GAIN', 'SPEND'),
    amount INT,
    description VARCHAR(255),
    transaction_date_time DATETIME(6),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE point_wallet (
    id binary(16) NOT NULL PRIMARY KEY,
    transactor_id binary(16),
    balance INT,
    version BIGINT DEFAULT 0 NOT NULL,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

-- input sample data
-- 현재 참여중인 퀘스트는 테스트 실행 시점의 날짜로 데이터를 생성해야한다.
-- global_variables 테이블을 활용해 현재 시간을 공통으로 관리하고 세션 변수에 할당해 사용한다.
CREATE TABLE global_variables (
    name VARCHAR(255) PRIMARY KEY,
    value DATETIME
);

INSERT INTO global_variables (name, value)
VALUES ('current_date_time', CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul'))
    ON DUPLICATE KEY UPDATE value = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul');

SET @current_date_time = (SELECT value FROM global_variables WHERE name = 'current_date_time');

INSERT INTO member (
    id,
    email,
    password,
    profile_image_url,
    handle,
    name,
    motto,
    daily_quest_threshold,
    weekly_quest_threshold,
    monthly_quest_threshold,
    login_provider,
    role_type,
    subscription_plan,
    activate_status,
    sign_up_date_time,
    last_login_date_time,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by,
    deleted_at
) VALUES
    (UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'gomotest@naver.com',
    '$2a$10$jlCrDrKargI8AhuSrS4FOuOX6e0w9a0F63/Norho6TGefGuljB2WC',
    'https://mini-cloud/gomotest-profile',
    '@GOMOTEST',
    'gomotest',
    'gomotest fighting!',
    10,
    5,
    0, -- create quest document test: 생성 제한 테스트를 위해 임계치를 0으로 고정한다.
    'EMAIL',
    'ROLE_MEMBER',
    'FREE',
    'ACTIVE',
    '2025-01-20T20:36:37.591469',
    '2025-01-20T20:36:37.591469',
    '2025-01-20T20:36:37.591469',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-20T20:36:37.591469',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    null),

    (UNHEX(REPLACE('b10581ce-d721-11ef-a8a5-250872a6438b', '-', '')),
    'gomotest2@naver.com',
    '$2a$10$jlCrDrKargI8AhuSrS4FOuOX6e0w9a0F63/Norho6TGefGuljB2WC',
    'https://mini-cloud/gomotest-profile',
    '@GOMOTEST2',
    'gomotest',
    'gomotest fighting!',
    7,
    7,
    7,
     'EMAIL',
    'ROLE_MEMBER',
    'FREE',
    'ACTIVE',
    @current_date_time,
    @current_date_time,
    @current_date_time,
    'b10581ce-d721-11ef-a8a5-250872a6438b',
    @current_date_time,
    'b10581ce-d721-11ef-a8a5-250872a6438b',
    null);

INSERT INTO interest(
    id,
    registrant_id,
    level,
    score,
    score_threshold,
    total_score,
    name,
    logo_url,
    version,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    (UNHEX(REPLACE('3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    10,
    45,
    60,
    445,
    "Backend",
    'https://mini-cloud/backend-logo.png',
    0,
    '2025-01-02T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-02T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),

    (UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    6,
    30,
    40,
    270,
    "Java",
    'https://mini-cloud/java-logo.png',
    0,
    '2025-01-10T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-10T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),

    (UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    5,
    20,
    40,
    220,
    "Spring",
    'https://mini-cloud/spring-logo.png',
    0,
    '2025-01-18T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO major_interest(
    id,
    registrant_id,
    interest_id,
    display_order,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    -- interest: java
    (UNHEX(REPLACE('172916b7-d7f6-11ef-b7fd-0bd5a273f618', '-', '')),
     UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    1,
    '2025-01-21T21:49:02.287881',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T21:49:02.287881',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- interest: spring
    (UNHEX(REPLACE('bec49c34-d7f5-11ef-8497-edeb32532766', '-', '')),
     UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    2,
    '2025-01-21T21:46:33.988955',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T21:46:33.988955',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO interest_relation(
    id,
    registrant_id,
    parent_interest_id,
    child_interest_id,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    -- java to backend relation
    (UNHEX(REPLACE('80ac5a74-d7eb-11ef-a2bd-1f5e37eb89a8', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    '2025-01-21T20:33:14.844656',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T20:33:14.844656',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO repeat_quest (
    id,
    participant_id,
    subject_id,
    subject_name,
    quest_type,
    content,
    display_order,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    -- repeat quest: java daily
    (UNHEX(REPLACE('3892ced2-d816-11ef-a05f-25caca7d3e8c', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    "Java",
    'DAILY',
    'Java 공식 문서 학습하고 TIL 작성하기',
    1,
    '2025-01-22T01:39:02.242071',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-22T01:39:02.242071',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- repeat quest: spring daily
    (UNHEX(REPLACE('a49f544f-d816-11ef-969c-6f84f91c1c7d', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    "Spring",
    'DAILY',
    'Spring 공식 문서 학습하고 TIL 작성하기',
    2,
    '2025-01-22T01:42:03.516094',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-22T01:42:03.516094',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO assign_quest(
    id,
    participant_id,
    subject_id,
    subject_name,
    quest_type,
    content,
    proof,
    is_confirmed,
    is_completed,
    display_order,
    start_date_time,
    completed_date_time,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    -- java daily quest - not confirm, not completed
    (UNHEX(REPLACE('1450177f-d7ff-11ef-830c-233264c36b07', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    'Java',
    'DAILY',
    'Java Stream API 학습하고 TIL 작성하기',
     'no_proof',
    false,
    false,
    1,
    '2025-01-21T22:53:22.980611',
    null,
    '2025-01-21T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- spring daily quest - confirm, not completed
    (UNHEX(REPLACE('bf259c7a-d7ff-11ef-ac7f-3bd3057a2c2e', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    'Spring',
    'DAILY',
    'Spring AOP 학습하고 TIL 작성하기',
    'no_proof',
     true,
    false,
    2,
    '2025-01-21T22:53:22.980611',
    null,
    '2025-01-21T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- java daily quest - confirm, completed
    (UNHEX(REPLACE('210891d5-d814-11ef-9cc5-cdb1eaaaac96', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    'Java',
    'DAILY',
    'Java Stream API 학습하고 TIL 작성하기',
     'https://proof',
    true,
    true,
    1,
    '2025-01-21T20:53:22.980611',
    '2025-01-21T22:53:22.980611',
    '2025-01-21T20:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- spring daily quest - confirm, completed
    (UNHEX(REPLACE('996604d8-d814-11ef-8d8d-fdccfa1ea3b3', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    'Spring',
    'DAILY',
    'Spring AOP 학습하고 TIL 작성하기',
     'https://proof',
    true,
    true,
    2,
    '2025-01-20T20:53:22.980611',
    '2025-01-20T22:53:22.980611',
    '2025-01-20T20:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-20T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- daily participating quest
    (UNHEX(REPLACE('0194cbd7-8689-74ec-bd46-dc855f493c3b', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    'Java',
    'DAILY',
    'daily participating quest',
    'no_proof',
    false,
    false,
    1,
    @current_date_time,
    NULL,
    @current_date_time,
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    @current_date_time,
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- weekly participating quest
    (UNHEX(REPLACE('0194cbda-6135-79fc-b659-ebaac3684761', '-', '')),
     UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
     UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
     'Java',
     'WEEKLY',
     'weekly participating quest',
     'no_proof',
     false,
     false,
     1,
     @current_date_time,
     NULL,
     @current_date_time,
     'a10581ce-d721-11ef-a8a5-2508e2a6438b',
     @current_date_time,
     'a10581ce-d721-11ef-a8a5-2508e2a6438b'),
    -- monthly participating quest
    (UNHEX(REPLACE('0194cbeb-345e-74a6-9199-07bdb402ea36', '-', '')),
     UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
     UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
     'Java',
     'MONTHLY',
     'monthly participating quest',
     'no_proof',
     false,
     false,
     1,
     @current_date_time,
     NULL,
     @current_date_time,
     'a10581ce-d721-11ef-a8a5-2508e2a6438b',
     @current_date_time,
     'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO streak (
    id,
    achiever_id,
    streak_type,
    filled_date,
    completed_quest_count,
    version,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    (UNHEX(REPLACE('203ece27-d868-11ef-824e-338baee5b682', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'DAILY',
    '2025-01-18',
    1,
    0,
    '2025-01-18T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-18T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),

    (UNHEX(REPLACE('fa1cf1b5-e3d3-11ef-9c0c-5f1ac1d71b42', '-', '')),
     UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
     'DAILY',
     '2025-02-06',
     1,
     0,
     '2025-02-06T00:00:00.000000',
     'a10581ce-d721-11ef-a8a5-2508e2a6438b',
     '2025-02-06T00:00:00.000000',
     'a10581ce-d721-11ef-a8a5-2508e2a6438b'),

    (UNHEX(REPLACE('febad463-d868-11ef-826f-395aa04e34d3', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'WEEKLY',
    '2025-01-20',
    1,
    0,
    '2025-01-20T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-20T22:53:22.980611',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO point (
    id,
    transactor_id,
    source_type,
    transaction_type,
    amount,
    description,
    transaction_date_time,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    (UNHEX(REPLACE('7dbbdedb-d734-11ef-9c48-394e79c4a67c', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'QUEST',
    'GAIN',
    10,
    '일일 퀘스트 완료 포인트 획득',
    '2025-01-20T22:47:25.429471',
    '2025-01-20T22:47:25.429471',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-20T22:47:25.429471',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),

    (UNHEX(REPLACE('c9f68773-d735-11ef-9d10-57f4db82cd9c', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'QUEST',
    'GAIN',
    150,
    '주간 퀘스트 완료 포인트 획득',
    '2025-01-21T22:47:25.429471',
    '2025-01-21T22:47:25.429471',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T22:47:25.429471',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b'),

    (UNHEX(REPLACE('ead8cd48-d735-11ef-b568-8745309ead01', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'QUEST',
    'GAIN',
    1500,
    '월간 퀘스트 완료 포인트 획득',
    '2025-01-22T22:47:25.429471',
    '2025-01-22T22:47:25.429471',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-22T22:47:25.429471',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO point_wallet (
    id,
    transactor_id,
    balance,
    version,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
      (UNHEX(REPLACE('e23db9d3-e6e5-11ef-9f07-0b157ee08b8d', '-', '')),
       UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
       '1660',
       0,
       '2025-02-09T22:47:25.429471',
       'a10581ce-d721-11ef-a8a5-2508e2a6438b',
       '2025-02-09T22:47:25.429471',
       'a10581ce-d721-11ef-a8a5-2508e2a6438b');

INSERT INTO survey_question (
    id,
    question_select_type,
    is_required,
    content,
    display_order,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    (UNHEX(REPLACE('3030e03f-d874-11ef-8c53-0f7e2e2f64c0', '-','')),
    'SINGLE',
    true,
    '직업이 무엇인가요?',
    1,
    '2025-01-22T12:51:40.869541',
    'admin',
    '2025-01-22T12:51:40.869541',
    'admin');

INSERT INTO survey_item (
    id,
    survey_question_id,
    content,
    display_order,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    (UNHEX(REPLACE('a115056c-d874-11ef-8a6a-454f4733cb7c', '-','')),
    UNHEX(REPLACE('3030e03f-d874-11ef-8c53-0f7e2e2f64c0', '-','')),
    '학생',
    1,
    '2025-01-22T12:51:40.869541',
    'admin',
    '2025-01-22T12:51:40.869541',
    'admin'),

    (UNHEX(REPLACE('b94a39ed-d874-11ef-b96f-c12f103e5c2c', '-','')),
    UNHEX(REPLACE('3030e03f-d874-11ef-8c53-0f7e2e2f64c0', '-','')),
    '기타',
    2,
    '2025-01-22T12:51:40.869541',
    'admin',
    '2025-01-22T12:51:40.869541',
    'admin');

INSERT INTO score_threshold_policy (
    level,
    threshold
) VALUES
    (0, 40),
    (1, 40),
    (2, 40),
    (3, 40),
    (4, 40),
    (5, 40),
    (6, 40),
    (7, 40),
    (8, 40),
    (9, 40),

    (10, 60),
    (11, 60),
    (12, 60),
    (13, 60),
    (14, 60),
    (15, 60),
    (16, 60),
    (17, 60),
    (18, 60),
    (19, 60),

    (20, 80),
    (21, 80),
    (22, 80),
    (23, 80),
    (24, 80),
    (25, 80),
    (26, 80),
    (27, 80),
    (28, 80),
    (29, 80),

    (30, 100),
    (31, 100),
    (32, 100),
    (33, 100),
    (34, 100),
    (35, 100),
    (36, 100),
    (37, 100),
    (38, 100),
    (39, 100),

    (40, 120),
    (41, 120),
    (42, 120),
    (43, 120),
    (44, 120),
    (45, 120),
    (46, 120),
    (47, 120),
    (48, 120),
    (49, 120),

    (50, 140),
    (51, 140),
    (52, 140),
    (53, 140),
    (54, 140),
    (55, 140),
    (56, 140),
    (57, 140),
    (58, 140),
    (59, 140),

    (60, 160),
    (61, 160),
    (62, 160),
    (63, 160),
    (64, 160),
    (65, 160),
    (66, 160),
    (67, 160),
    (68, 160),
    (69, 160),

    (70, 180),
    (71, 180),
    (72, 180),
    (73, 180),
    (74, 180),
    (75, 180),
    (76, 180),
    (77, 180),
    (78, 180),
    (79, 180),

    (80, 200),
    (81, 200),
    (82, 200),
    (83, 200),
    (84, 200),
    (85, 200),
    (86, 200),
    (87, 200),
    (88, 200),
    (89, 200),

    (90, 220),
    (91, 220),
    (92, 220),
    (93, 220),
    (94, 220),
    (95, 220),
    (96, 220),
    (97, 220),
    (98, 220),
    (99, 220),
    (100, 10000);

INSERT INTO quest_score_policy(
    quest_type,
    score
) VALUES
    ('DAILY', 2),
    ('WEEKLY', 20),
    ('MONTHLY', 100);

INSERT INTO quest_point_policy(
    quest_type,
    points
) VALUES
    ('DAILY', 10),
    ('WEEKLY', 150),
    ('MONTHLY', 1500);