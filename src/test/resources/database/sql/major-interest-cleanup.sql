DELETE FROM major_interest;

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
    'a10581ce-d721-11ef-a8a5-2508e2a6438b',
    '2025-01-21T21:49:02.287884600',
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