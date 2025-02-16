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