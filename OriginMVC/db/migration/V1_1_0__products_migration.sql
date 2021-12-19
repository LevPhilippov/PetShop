CREATE TABLE IF NOT EXISTS params(
                                     id VARCHAR(36) NOT NULL,
                                     title VARCHAR NOT NULL,
                                     measure VARCHAR(32),
                                     version INT NOT NULL DEFAULT 0,
                                     created_at TIMESTAMP,
                                     updated_at TIMESTAMP,
                                     PRIMARY KEY (id)

);

INSERT INTO params (id, title, measure, version, created_at, updated_at ) VALUES
('72746941-51b7-48bf-85cf-a440386e9ae7', 'Taste', NULL, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('e0429a9c-1972-45ce-a969-67f32bb46640', 'Calories', 'kkal', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('18f43c9c-f40c-4ad4-bd39-3302c28e3a2b', 'Value', 'ml', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('653f8ace-ba5e-4cfd-9dd0-cf533dbe1cdf', 'Ingredients', NULL, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('57d26cf7-7035-468f-8e9f-00eecc5b2699', 'Style', NULL, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('855d3fb0-36f1-494b-958b-4d03ea8202c3', 'Manufacturer', NULL, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS inventories(
                                          id VARCHAR(36) NOT NULL,
                                          qty INT NOT NULL DEFAULT 0,
                                          measure VARCHAR(32) NOT NULL,
                                          product_id VARCHAR(36) NOT NULL,
                                          version INT,
                                          created_at TIMESTAMP,
                                          updated_at TIMESTAMP,
                                          PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories(
                                         id VARCHAR(36) NOT NULL,
                                         title VARCHAR,
                                         description VARCHAR NOT NULL,
                                         version INT,
                                         created_at TIMESTAMP,
                                         updated_at TIMESTAMP,
                                         PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS attributes(
                                         id VARCHAR(36) NOT NULL,
                                         value VARCHAR(32) NOT NULL,
                                         param_id VARCHAR(36) NOT NULL,
                                         version INT,
                                         created_at TIMESTAMP,
                                         updated_at TIMESTAMP,
                                         PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products(
                                       id VARCHAR(36) NOT NULL,
                                       title VARCHAR(32) NOT NULL,
                                       price DECIMAL NOT NULL,
                                       description VARCHAR NOT NULL,
                                       upc VARCHAR NOT NULL,
                                       category_id VARCHAR(36) NOT NULL,
                                       version INT,
                                       created_at TIMESTAMP,
                                       updated_at TIMESTAMP,
                                       PRIMARY KEY (id)
);
