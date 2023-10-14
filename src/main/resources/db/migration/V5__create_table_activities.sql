CREATE TABLE activities
(
    id             UUID         NOT NULL,
    name           VARCHAR(255) NOT NULL,
    book_type      VARCHAR(255) NOT NULL,
    create_user_id UUID         NOT NULL,
    update_user_id UUID,
    created_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_activities PRIMARY KEY (id)
);

ALTER TABLE activities
    ADD CONSTRAINT uc_activities_name UNIQUE (name);

ALTER TABLE sectors
    ADD CONSTRAINT uc_sectors_name UNIQUE (name);

CREATE INDEX activities_bookType_text_pattern_ops_idx ON activities (book_type);

CREATE INDEX activities_name_text_pattern_ops_idx ON activities (name);

ALTER TABLE sectors
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE sub_sectors
    ALTER COLUMN name SET NOT NULL;