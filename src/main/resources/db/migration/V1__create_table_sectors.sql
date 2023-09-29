CREATE TABLE sectors
(
    id             UUID    NOT NULL,
    name           VARCHAR(255),
    observations   VARCHAR(255),
    active         BOOLEAN NOT NULL,
    create_user_id UUID    NOT NULL,
    update_user_id UUID,
    created_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_sectors PRIMARY KEY (id)
);