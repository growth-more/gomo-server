DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS survey_question;
DROP TABLE IF EXISTS survey_item;
DROP TABLE IF EXISTS survey_result;
DROP TABLE IF EXISTS interest;
DROP TABLE IF EXISTS interest_relation;
DROP TABLE IF EXISTS major_interest;
DROP TABLE IF EXISTS score_threshold_policy;
DROP TABLE IF EXISTS streak;
DROP TABLE IF EXISTS repeat_quest;
DROP TABLE IF EXISTS assign_quest;
DROP TABLE IF EXISTS quest_score_policy;
DROP TABLE IF EXISTS quest_point_policy;
DROP TABLE IF EXISTS point;
DROP TABLE IF EXISTS point_wallet;
DROP TABLE IF EXISTS global_variables;
DROP TABLE IF EXISTS event_entry;
DROP TABLE IF EXISTS score_quest_completed_success_event;
DROP TABLE IF EXISTS streak_quest_completed_success_event;
DROP TABLE IF EXISTS point_quest_completed_success_event;

CREATE TABLE member (
    id BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    profile_banner_url VARCHAR(512),
    profile_image_url VARCHAR(512),
    handle VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(30) NOT NULL,
    motto VARCHAR(255),
    daily_quest_threshold TINYINT DEFAULT 1,
    weekly_quest_threshold TINYINT DEFAULT 1,
    monthly_quest_threshold TINYINT DEFAULT 1,
    login_provider ENUM('EMAIL', 'GOOGLE', 'KAKAO', 'NAVER') NOT NULL,
    role_type ENUM('ROLE_MEMBER', 'ROLE_ADMIN') NOT NULL,
    subscription_plan ENUM('FREE', 'BASIC', 'PREMIUM') NOT NULL,
    activate_status ENUM('ACTIVE', 'INACTIVE', 'BLOCKED', 'DELETED') NOT NULL,
    sign_up_date_time DATETIME(6),
    last_login_date_time DATETIME(6),
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255),
    deleted_at DATETIME(6)
);

CREATE TABLE interest (
    id BINARY(16) NOT NULL PRIMARY KEY,
    registrant_id BINARY(16),
    level INT,
    score INT,
    score_threshold INT,
    total_score INT,
    name VARCHAR(30),
    logo_url VARCHAR(512),
    color_code VARCHAR(10),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE interest_relation (
    id BINARY(16) NOT NULL PRIMARY KEY,
    registrant_id BINARY(16),
    parent_interest_id BINARY(16),
    child_interest_id BINARY(16),
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE major_interest (
    id BINARY(16) PRIMARY KEY,
    registrant_id BINARY(16) NOT NULL,
    interest_id BINARY(16) NOT NULL,
    display_order INT NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE score_threshold_policy (
    level SMALLINT,
    threshold INT
);

CREATE TABLE streak (
    id BINARY(16) NOT NULL PRIMARY KEY,
    achiever_id BINARY(16),
    streak_type ENUM('DAILY', 'WEEKLY', 'MONTHLY'),
    filled_date DATE,
    completed_quest_count TINYINT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE repeat_quest (
    id BINARY(16) NOT NULL PRIMARY KEY,
    participant_id BINARY(16),
    subject_id BINARY(16),
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
    id BINARY(16) NOT NULL PRIMARY KEY,
    participant_id BINARY(16),
    subject_id BINARY(16),
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
    id BINARY(16) NOT NULL PRIMARY KEY,
    transactor_id BINARY(16),
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
    id BINARY(16) NOT NULL PRIMARY KEY,
    transactor_id BINARY(16),
    balance INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE achiever (
    id BINARY(16) NOT NULL PRIMARY KEY,
    longest_streak_days INT,
    current_streak_days INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE event_entry (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    event_status ENUM('PENDING', 'COMPLETED', 'FAILED') NOT NULL,
    payload TEXT NOT NULL,
    timestamp BIGINT NOT NULL
);

CREATE TABLE score_quest_completed_success_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_entry_id BIGINT NOT NULL
);

CREATE TABLE streak_quest_completed_success_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_entry_id BIGINT NOT NULL
);

CREATE TABLE point_quest_completed_success_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_entry_id BIGINT NOT NULL
);

CREATE TABLE survey_question (
    id BINARY(16) NOT NULL PRIMARY KEY,
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
    id BINARY(16) NOT NULL PRIMARY KEY,
    survey_question_id BINARY(16),
    content VARCHAR(255),
    display_order INT,
    created_at DATETIME(6),
    created_by varchar(255),
    last_modified_at DATETIME(6),
    last_modified_by varchar(255)
);

CREATE TABLE survey_result (
    respondent_id BINARY(16),
    survey_question_id BINARY(16),
    survey_item_id BINARY(16),
    survey_item_content varchar(255),
    custom_answer varchar(255)
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
--
-- INSERT INTO member (
--     id,
--     email,
--     password,
--     profile_banner_url,
--     profile_image_url,
--     handle,
--     name,
--     motto,
--     daily_quest_threshold,
--     weekly_quest_threshold,
--     monthly_quest_threshold,
--     login_provider,
--     role_type,
--     subscription_plan,
--     activate_status,
--     sign_up_date_time,
--     last_login_date_time,
--     created_at,
--     created_by,
--     last_modified_at,
--     last_modified_by,
--     deleted_at
-- ) VALUES
--     (UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
--     'gomotest@naver.com',
--     '$2a$10$jlCrDrKargI8AhuSrS4FOuOX6e0w9a0F63/Norho6TGefGuljB2WC',
--     'DEFAULT_IMAGE',
--     'https://mini-cloud/gomotest-profile',
--     '@GOMOTEST',
--     'gomotest',
--     'gomotest fighting!',
--     10,
--     5,
--     0,
--     'EMAIL',
--     'ROLE_MEMBER',
--     'FREE',
--     'ACTIVE',
--      @current_date_time,
--      @current_date_time,
--      @current_date_time,
--      'a10581ce-d721-11ef-a8a5-2508e2a6438b',
--      @current_date_time,
--      'a10581ce-d721-11ef-a8a5-2508e2a6438b',
--      null),
--
--     (UNHEX(REPLACE('b10581ce-d721-11ef-a8a5-250872a6438b', '-', '')),
--     'gomotest2@naver.com',
--     '$2a$10$jlCrDrKargI8AhuSrS4FOuOX6e0w9a0F63/Norho6TGefGuljB2WC',
--     'DEFAULT_IMAGE',
--     'https://mini-cloud/gomotest-profile',
--     '@GOMOTEST2',
--     'gomotest',
--     'gomotest fighting!',
--     7,
--     7,
--     7,
--      'EMAIL',
--     'ROLE_MEMBER',
--     'FREE',
--     'ACTIVE',
--     @current_date_time,
--     @current_date_time,
--     @current_date_time,
--     'b10581ce-d721-11ef-a8a5-250872a6438b',
--     @current_date_time,
--     'b10581ce-d721-11ef-a8a5-250872a6438b',
--     null);

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
