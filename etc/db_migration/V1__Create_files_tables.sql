-- DROP TABLE IF EXISTS files

-- Beware of setting table and schema names into quotes, as h2 has default option DATABASE_TO_UPPER=true

CREATE TABLE files (
  `app` INT NOT NULL
, `version` VARCHAR(255)
, `platform` VARCHAR(255)
, `name` VARCHAR(255)
, `oidc_sub` VARCHAR(255)
, `size` BIGINT
, CONSTRAINT pk_fileID PRIMARY KEY (app,platform,version)
);