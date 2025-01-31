DELETE FROM interest_relation;

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