ALTER TABLE sectors
    ALTER COLUMN active DROP NOT NULL;

ALTER TABLE sub_sectors
    ALTER COLUMN sector_id SET NOT NULL;