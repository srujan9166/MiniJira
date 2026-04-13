-- V5__fix_fk_types.sql

ALTER TABLE issues
ALTER COLUMN assignee_id TYPE BIGINT;

ALTER TABLE issues
ALTER COLUMN reporter_id TYPE BIGINT;

ALTER TABLE issues
ALTER COLUMN project_id TYPE BIGINT;

ALTER TABLE issues
ALTER COLUMN sprint_id TYPE BIGINT;