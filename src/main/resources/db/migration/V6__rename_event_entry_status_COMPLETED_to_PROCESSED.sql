ALTER TABLE event_entry
MODIFY COLUMN event_status
ENUM('PENDING', 'PROCESSED', 'FAILED') NOT NULL;

UPDATE event_entry
SET event_status = 'PROCESSED'
WHERE event_status = 'COMPLETED';
