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
DROP TABLE IF EXISTS event_entry;
DROP TABLE IF EXISTS score_quest_completed_success_event;
DROP TABLE IF EXISTS streak_quest_completed_success_event;
DROP TABLE IF EXISTS point_quest_completed_success_event;

CREATE TABLE member (
    id BINARY(16) NOT NULL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    profile_banner_url VARCHAR(512),
    profile_image_url VARCHAR(512),
    profile_image_origin_name VARCHAR(255),
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
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255),
    deleted_at DATETIME(6)
);

CREATE TABLE survey_question (
    id BINARY(16) NOT NULL PRIMARY KEY,
    question_select_type VARCHAR(50),
    is_required TINYINT(1),
    content VARCHAR(255),
    display_order INT,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE survey_item (
    id BINARY(16) NOT NULL PRIMARY KEY,
    survey_question_id BINARY(16),
    content VARCHAR(255),
    display_order INT,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE survey_result (
    respondent_id BINARY(16),
    survey_question_id BINARY(16),
    survey_item_id BINARY(16),
    survey_item_content VARCHAR(255),
    custom_answer VARCHAR(255)
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
    version BIGINT DEFAULT 0 NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE interest_relation (
    id BINARY(16) NOT NULL PRIMARY KEY,
    registrant_id BINARY(16),
    parent_interest_id BINARY(16),
    child_interest_id BINARY(16),
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE major_interest (
    id BINARY(16) NOT NULL PRIMARY KEY,
    registrant_id BINARY(16),
    interest_id BINARY(16),
    display_order INT,
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
    version BIGINT DEFAULT 0 NOT NULL,
    created_at DATETIME(6),
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
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
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
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
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
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
    created_by VARCHAR(255),
    last_modified_at DATETIME(6),
    last_modified_by VARCHAR(255)
);

CREATE TABLE point_wallet (
    id BINARY(16) NOT NULL PRIMARY KEY,
    transactor_id BINARY(16),
    balance INT,
    version BIGINT DEFAULT 0 NOT NULL,
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