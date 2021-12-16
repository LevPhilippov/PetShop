CREATE TABLE IF NOT EXISTS inventory(
    id bigserial NOT NULL,
    qty BIGINT NOT NULL,
    version INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS birds (
    id bigserial NOT NULL,
    title VARCHAR NOT NULL,
    price DECIMAL NOT NULL,
    qty INT NOT NULL,
    animal_type VARCHAR NOT NULL,
    legs_id BIGINT,
    body_id BIGINT,
    wings_id BIGINT,
    version INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mammals (
    id bigserial NOT NULL,
    title VARCHAR NOT NULL,
    price DECIMAL NOT NULL,
    qty INT NOT NULL,
    animal_type VARCHAR NOT NULL,
    legs_id BIGINT,
    body_id BIGINT,
    version INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS legs_components (
    id bigserial NOT NULL,
    qty_of_legs BIGINT NOT NULL,
    lengh_of_legs BIGINT NOT NULL,
    version INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS wings_components (
   id bigserial NOT NULL,
   wingspan BIGINT NOT NULL,
   lift_power BIGINT NOT NULL,
   version INT,
   created_at TIMESTAMP,
   updated_at TIMESTAMP,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS body_components (
   id bigserial NOT NULL,
   weight BIGINT NOT NULL,
   version INT,
   created_at TIMESTAMP,
   updated_at TIMESTAMP,
   PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS params (
      id bigserial NOT NULL,
      name VARCHAR NOT NULL,
      value VARCHAR NOT NULL,
      type_of_measure VARCHAR,
      version INT,
      created_at TIMESTAMP,
      updated_at TIMESTAMP,
      PRIMARY KEY (id)
);


