DELETE FROM repeat_quest;

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