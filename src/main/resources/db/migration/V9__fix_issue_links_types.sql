ALTER TABLE issue_links
ALTER COLUMN id TYPE BIGINT;

ALTER TABLE issue_links
ALTER COLUMN source_issue_id TYPE BIGINT;

ALTER TABLE issue_links
ALTER COLUMN target_issue_id TYPE BIGINT;