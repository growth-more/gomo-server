drop table if exists member;
drop table if exists survey_question;
drop table if exists survey_item;
drop table if exists survey_result;
drop table if exists interest;
drop table if exists interest_relation;
drop table if exists major_interest;
drop table if exists score_threshold;
drop table if exists streak;
drop table if exists repeat_quest;
drop table if exists assign_quest;
drop table if exists quest_score_policy;
drop table if exists quest_point_policy;
drop table if exists point;

CREATE TABLE member (
    id binary(16) NOT NULL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    profile_image_url VARCHAR(512),
    profile_image_origin_name VARCHAR(255),
    handle VARCHAR(50) UNIQUE,
    name VARCHAR(30),
    motto VARCHAR(255),
    daily_quest_threshold TINYINT,
    weekly_quest_threshold TINYINT,
    monthly_quest_threshold TINYINT,
    role_type ENUM('ROLE_MEMBER', 'ROLE_ADMIN'),
    subscription_plan ENUM('FREE', 'BASIC', 'PREMIUM'),
    activate_status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED', 'DELETED'),
    sign_up_date_time DATETIME(6),
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
    text TEXT
);

CREATE TABLE interest (
    id binary(16) NOT NULL PRIMARY KEY,
    registrant_id binary(16),
    level INT,
    score INT,
    total_score INT,
    name VARCHAR(30),
    logo_url VARCHAR(512),
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

CREATE TABLE score_threshold (
    min_level SMALLINT,
    max_level SMALLINT,
    threshold INT
);

CREATE TABLE streak (
    id binary(16) NOT NULL PRIMARY KEY,
    achiever_id binary(16),
    streak_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    filled_date DATE,
    completed_quest_count TINYINT,
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
    started_date_time DATETIME(6),
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
    point_type ENUM('QUEST', 'ATTENDANCE', 'STORE', 'EVENT'),
    transaction_type ENUM('GAIN', 'SPEND'),
    points INT,
    description VARCHAR(255),
    transaction_date DATETIME(6),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

-- input sample data
INSERT INTO member (
    id,
    email,
    password,
    profile_image_url,
    profile_image_origin_name,
    handle,
    name,
    motto,
    daily_quest_threshold,
    weekly_quest_threshold,
    monthly_quest_threshold,
    role_type,
    subscription_plan,
    activate_status,
    sign_up_date_time,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by,
    deleted_at
) VALUES (
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'gomotest@naver.com',
    'gomotest1234@',
    'https://mini-cloud/gomotest-profile',
    'gomotest-profile.png',
    '@GOMOTEST',
    'gomotest',
    'gomotest fighting!',
    10,
    5,
    3,
    'ROLE_MEMBER',
    'FREE',
    'ACTIVE',
    '2025-01-20T20:36:37.591469',
    '2025-01-20T20:36:37.591469',
    'gomotest@naver.com',
    '2025-01-20T20:36:37.591469',
    'gomotest@naver.com',
    null
);

INSERT INTO interest(
    id,
    registrant_id,
    level,
    score,
    total_score,
    name,
    logo_url,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by
) VALUES
    (UNHEX(REPLACE('3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    10,
    450,
    4450,
    "Backend",
    'https://mini-cloud/backend-logo.png',
    '2025-01-02T16:04:35.457921900',
    'gomotest@naver.com',
    '2025-01-02T16:04:35.457921900',
    'gomotest@naver.com'),

    (UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    6,
    30,
    2430,
    "Java",
    'https://mini-cloud/java-logo.png',
    '2025-01-10T16:04:35.457921900',
    'gomotest@naver.com',
    '2025-01-10T16:04:35.457921900',
    'gomotest@naver.com'),

    (UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    5,
    20,
    2020,
    "Spring",
    'https://mini-cloud/spring-logo.png',
    '2025-01-18T16:04:35.457921900',
    'gomotest@naver.com',
    '2025-01-21T16:04:35.457921900',
    'gomotest@naver.com');

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
    '2025-01-21T21:49:02.287884600',
    'gomotest@naver.com',
    '2025-01-21T21:49:02.287884600',
    'gomotest@naver.com'),
    -- interest: spring
    (UNHEX(REPLACE('bec49c34-d7f5-11ef-8497-edeb32532766', '-', '')),
     UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    2,
    '2025-01-21T21:46:33.988955',
    'gomotest@naver.com',
    '2025-01-21T21:46:33.988955',
    'gomotest@naver.com');

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
    'gomotest@naver.com',
    '2025-01-21T20:33:14.844656',
    'gomotest@naver.com');

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
    '2025-01-22T01:39:02.242078300',
    'gomotest@naver.com',
    '2025-01-22T01:39:02.242078300',
    'gomotest@naver.com'),
    -- repeat quest: spring daily
    (UNHEX(REPLACE('a49f544f-d816-11ef-969c-6f84f91c1c7d', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    "Spring",
    'DAILY',
    'Spring 공식 문서 학습하고 TIL 작성하기',
    2,
    '2025-01-22T01:42:03.516094',
    'gomotest@naver.com',
    '2025-01-22T01:42:03.516094',
    'gomotest@naver.com');

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
    started_date_time,
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
    null,
    false,
    false,
    1,
    '2025-01-21T22:53:22.980610300',
    null,
    '2025-01-21T22:53:22.980610300',
    'gomotest@naver.com',
    '2025-01-21T22:53:22.980610300',
    'gomotest@naver.com'),
    -- spring daily quest - not confirm, not completed
    (UNHEX(REPLACE('bf259c7a-d7ff-11ef-ac7f-3bd3057a2c2e', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    'Spring',
    'DAILY',
    'Spring AOP 학습하고 TIL 작성하기',
    null,
    false,
    false,
    2,
    '2025-01-21T22:53:22.980610300',
    null,
    '2025-01-21T22:53:22.980610300',
    'gomotest@naver.com',
    '2025-01-21T22:53:22.980610300',
    'gomotest@naver.com'),
    -- java daily quest - confirm, completed
    (UNHEX(REPLACE('210891d5-d814-11ef-9cc5-cdb1eaaaac96', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('f8c51811-d7c5-11ef-82dc-4322ccc3e338', '-', '')),
    'Java',
    'DAILY',
    'Java Stream API 학습하고 TIL 작성하기',
    null,
    true,
    true,
    1,
    '2025-01-18T20:53:22.980610300',
    '2025-01-18T22:53:22.980610300',
    '2025-01-18T20:53:22.980610300',
    'gomotest@naver.com',
    '2025-01-18T22:53:22.980610300',
    'gomotest@naver.com'),
    -- spring daily quest - confirm, completed
    (UNHEX(REPLACE('996604d8-d814-11ef-8d8d-fdccfa1ea3b3', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    UNHEX(REPLACE('90a387a7-d7c5-11ef-b4d7-079c7dc41274', '-', '')),
    'Spring',
    'DAILY',
    'Spring AOP 학습하고 TIL 작성하기',
    null,
    true,
    true,
    2,
    '2025-01-20T20:53:22.980610300',
    '2025-01-20T22:53:22.980610300',
    '2025-01-20T20:53:22.980610300',
    'gomotest@naver.com',
    '2025-01-20T22:53:22.980610300',
    'gomotest@naver.com');

INSERT INTO streak (
    id,
    achiever_id,
    streak_type,
    filled_date,
    completed_quest_count,
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
    '2025-01-18T22:53:22.980610300',
    'gomotest@naver.com',
    '2025-01-18T22:53:22.980610300',
    'gomotest@naver.com'),

    (UNHEX(REPLACE('febad463-d868-11ef-826f-395aa04e34d3', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'DAILY',
    '2025-01-20',
    1,
    '2025-01-20T22:53:22.980610300',
    'gomotest@naver.com',
    '2025-01-20T22:53:22.980610300',
    'gomotest@naver.com');

INSERT INTO point (
    id,
    transactor_id,
    point_type,
    transaction_type,
    points,
    description,
    transaction_date,
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
    '2025-01-20T22:47:25.429479500',
    '2025-01-20T22:47:25.429479500',
    'gomotest@naver.com',
    '2025-01-20T22:47:25.429479500',
    'gomotest@naver.com'),

    (UNHEX(REPLACE('c9f68773-d735-11ef-9d10-57f4db82cd9c', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'QUEST',
    'GAIN',
    150,
    '주간 퀘스트 완료 포인트 획득',
    '2025-01-21T22:47:25.429479500',
    '2025-01-21T22:47:25.429479500',
    'gomotest@naver.com',
    '2025-01-21T22:47:25.429479500',
    'gomotest@naver.com'),

    (UNHEX(REPLACE('ead8cd48-d735-11ef-b568-8745309ead01', '-', '')),
    UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'QUEST',
    'GAIN',
    1500,
    '월간 퀘스트 완료 포인트 획득',
    '2025-01-22T22:47:25.429479500',
    '2025-01-22T22:47:25.429479500',
    'gomotest@naver.com',
    '2025-01-22T22:47:25.429479500',
    'gomotest@naver.com');

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
    '2025-01-22T12:51:40.869541100',
    'admin',
    '2025-01-22T12:51:40.869541100',
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
    '2025-01-22T12:51:40.869541100',
    'admin',
    '2025-01-22T12:51:40.869541100',
    'admin'),

    (UNHEX(REPLACE('b94a39ed-d874-11ef-b96f-c12f103e5c2c', '-','')),
    UNHEX(REPLACE('3030e03f-d874-11ef-8c53-0f7e2e2f64c0', '-','')),
    '기타',
    2,
    '2025-01-22T12:51:40.869541100',
    'admin',
    '2025-01-22T12:51:40.869541100',
    'admin');

INSERT INTO score_threshold (
    min_level,
    max_level,
    threshold
) VALUES
    (0, 9, 400),
    (10, 19, 600),
    (20, 29, 800),
    (30, 39, 1000),
    (40, 49, 1200),
    (50, 59, 1400),
    (60, 69, 1600),
    (70, 79, 1800),
    (80, 89, 2000),
    (90, 99, 2200),
    (100, 100, 10000);

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