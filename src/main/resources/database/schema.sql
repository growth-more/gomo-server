DROP TABLE IF EXISTS member;
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
DROP TABLE IF EXISTS event_entry;
DROP TABLE IF EXISTS score_quest_completed_success_event;
DROP TABLE IF EXISTS streak_quest_completed_success_event;
DROP TABLE IF EXISTS point_quest_completed_success_event;
DROP TABLE IF EXISTS survey_question;
DROP TABLE IF EXISTS survey_item;
DROP TABLE IF EXISTS survey_result;

CREATE TABLE member (
    id BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    profile_banner_url VARCHAR(512),
    profile_image_url VARCHAR(512),
    profile_image_origin_name VARCHAR(255),
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
    id BINARY(16) PRIMARY KEY,
    registrant_id BINARY(16) NOT NULL,
    level INT DEFAULT 0,
    score INT DEFAULT 0,
    score_threshold INT DEFAULT 40,
    total_score INT DEFAULT 0,
    name VARCHAR(30) NOT NULL,
    logo_url VARCHAR(512),
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE interest_relation (
    id BINARY(16) PRIMARY KEY,
    registrant_id BINARY(16) NOT NULL,
    parent_interest_id BINARY(16) NOT NULL,
    child_interest_id BINARY(16) NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
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
    level SMALLINT NOT NULL,
    threshold INT NOT NULL
);

CREATE TABLE streak (
    id BINARY(16) PRIMARY KEY,
    achiever_id BINARY(16) NOT NULL,
    streak_type ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    filled_date DATE NOT NULL,
    completed_quest_count TINYINT NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE repeat_quest (
    id BINARY(16) PRIMARY KEY,
    participant_id BINARY(16) NOT NULL,
    subject_id BINARY(16) NOT NULL,
    subject_name VARCHAR(30) NOT NULL,
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    content VARCHAR(255) NOT NULL,
    display_order INT NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE assign_quest (
    id BINARY(16) PRIMARY KEY,
    participant_id BINARY(16) NOT NULL,
    subject_id BINARY(16) NOT NULL,
    subject_name VARCHAR(30) NOT NULL,
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    content VARCHAR(255) NOT NULL,
    proof VARCHAR(512),
    is_confirmed TINYINT(1),
    is_completed TINYINT(1),
    display_order INT NOT NULL,
    start_date_time DATETIME(6) NOT NULL,
    completed_date_time DATETIME(6),
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE quest_score_policy (
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    score INT NOT NULL
);

CREATE TABLE quest_point_policy (
    quest_type ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    points INT NOT NULL
);

CREATE TABLE point (
    id BINARY(16) PRIMARY KEY,
    transactor_id BINARY(16) NOT NULL,
    source_type ENUM('QUEST', 'ATTENDANCE', 'STORE', 'EVENT') NOT NULL,
    transaction_type ENUM('GAIN', 'SPEND') NOT NULL,
    amount INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    transaction_date_time DATETIME(6) NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE point_wallet (
    id BINARY(16) PRIMARY KEY,
    transactor_id BINARY(16) NOT NULL,
    balance INT NOT NULL DEFAULT 0,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
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
    id BINARY(16) PRIMARY KEY,
    question_select_type ENUM('SINGLE', 'MULTIPLE'),
    is_required TINYINT(1) NOT NULL,
    content VARCHAR(255) NOT NULL,
    display_order INT NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE survey_item (
    id BINARY(16) PRIMARY KEY,
    survey_question_id BINARY(16) NOT NULL,
    content VARCHAR(255) NOT NULL,
    display_order INT NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE survey_result (
    respondent_id BINARY(16) NOT NULL,
    survey_question_id BINARY(16) NOT NULL,
    survey_item_id BINARY(16) NOT NULL,
    survey_item_content VARCHAR(255) NOT NULL,
    custom_answer VARCHAR(255)
);