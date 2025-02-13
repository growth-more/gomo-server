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
    last_login_date_time,
    created_at,
    created_by,
    last_modified_at,
    last_modified_by,
    deleted_at
) VALUES
    (UNHEX(REPLACE('a10581ce-d721-11ef-a8a5-2508e2a6438b', '-', '')),
    'gomo-admin@naver.com',
    'gomoadmin@',
    'https://mini-cloud/gomo-admin',
    'gomo-admin.png',
    '@GOMO',
    'admin',
    '.',
    0,
    0,
    0,
    'ROLE_ADMIN',
    'FREE',
    'ACTIVE',
    '2025-01-20T20:36:37.591469',
    '2025-01-20T20:36:37.591469',
    '2025-01-20T20:36:37.591469',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-20T20:36:37.591469',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
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
    0,
    0,
    40,
    0,
    "BLANK",
    'https://mini-cloud/blank.png',
    0,
    '2025-01-02T16:04:35.457921',
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-02T16:04:35.457921',
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
    (0, 9, 40),
    (10, 19, 60),
    (20, 29, 80),
    (30, 39, 100),
    (40, 49, 120),
    (50, 59, 140),
    (60, 69, 160),
    (70, 79, 180),
    (80, 89, 200),
    (90, 99, 220),
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