CREATE TABLE prayer_house_activities
(
    activity_id      UUID NOT NULL,
    praying_house_id UUID NOT NULL
);

CREATE TABLE prayer_houses
(
    id             UUID         NOT NULL,
    name           VARCHAR(255) NOT NULL,
    sector_id      UUID         NOT NULL,
    sub_sector_id  UUID,
    create_user_id UUID         NOT NULL,
    update_user_id UUID,
    created_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_prayer_houses PRIMARY KEY (id)
);

ALTER TABLE prayer_houses
    ADD CONSTRAINT uc_prayer_houses_name UNIQUE (name);

CREATE INDEX prayer_houses_name_text_pattern_ops_idx ON prayer_houses (name);

ALTER TABLE prayer_houses
    ADD CONSTRAINT FK_PRAYER_HOUSES_ON_SECTOR FOREIGN KEY (sector_id) REFERENCES sectors (id);

CREATE INDEX prayer_houses_sector_id_text_idx ON prayer_houses (sector_id);

ALTER TABLE prayer_houses
    ADD CONSTRAINT FK_PRAYER_HOUSES_ON_SUB_SECTOR FOREIGN KEY (sub_sector_id) REFERENCES sub_sectors (id);

CREATE INDEX prayer_houses_sub_sector_id_text_idx ON prayer_houses (sub_sector_id);

ALTER TABLE prayer_house_activities
    ADD CONSTRAINT fk_prahouact_on_activity_entity FOREIGN KEY (activity_id) REFERENCES activities (id);

ALTER TABLE prayer_house_activities
    ADD CONSTRAINT fk_prahouact_on_praying_house_entity FOREIGN KEY (praying_house_id) REFERENCES prayer_houses (id);

CREATE INDEX IF NOT EXISTS activities_name_text_pattern_ops_idx ON activities (name);