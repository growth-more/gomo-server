DELETE FROM point;
DELETE FROM point_wallet;

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
     '2025-02-09T22:47:25.4294710',
     'gomotest@naver.com',
     '2025-02-09T22:47:25.429471',
     'gomotest@naver.com');