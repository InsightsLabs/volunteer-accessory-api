CREATE TABLE sub_sectors
(
    id             UUID    NOT NULL,
    name           VARCHAR(255),
    observations   VARCHAR(255),
    active         BOOLEAN NOT NULL,
    sector_id      UUID,
    create_user_id UUID    NOT NULL,
    update_user_id UUID,
    created_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_sub_sectors PRIMARY KEY (id)
);

CREATE INDEX idx_sub_sector_name ON sub_sectors (name);

ALTER TABLE sub_sectors
    ADD CONSTRAINT FK_SUB_SECTORS_ON_SECTOR FOREIGN KEY (sector_id) REFERENCES sectors (id);