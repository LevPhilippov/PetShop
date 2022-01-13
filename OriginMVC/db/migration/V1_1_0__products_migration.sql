CREATE TABLE IF NOT EXISTS params(
                                     id VARCHAR(36),
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


CREATE TABLE IF NOT EXISTS categories(
                                         id VARCHAR(36),
                                         title VARCHAR,
                                         description VARCHAR NOT NULL,
                                         version INT,
                                         created_at TIMESTAMP,
                                         updated_at TIMESTAMP,
                                         PRIMARY KEY (id)
);

INSERT INTO categories(id, title, description, version, created_at, updated_at) VALUES
('1f3cb550-2df7-4638-980e-f777e08e1c67','Beer','Refreshing beverages',0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ed3cd853-341d-40a4-975b-78e15d862bca','Wine','Refreshing beverages',0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('5e659e17-f131-454b-80fc-17c53050282d','Gin','Refreshing beverages',0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


CREATE TABLE IF NOT EXISTS products(
                                       id VARCHAR(36),
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

INSERT INTO products(id, title, price, description, upc, category_id, version, created_at, updated_at) VALUES
('dd905ae3-3541-4809-8c06-387378b26420', 'Spaten', 179.99, 'Genuine german lager beer from heart of Munchen', '1234567890', '1f3cb550-2df7-4638-980e-f777e08e1c67', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('f0c04d82-0649-45c5-a9ba-20cbbe423781', 'Guinness', 250.00, 'Irish creamy stout for old British classic lovers', '1234567890', '1f3cb550-2df7-4638-980e-f777e08e1c67', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('e4d7d687-bc29-478d-bd17-8e5e42fe5a1e', 'Brunello di Montalcino', 4375.00, 'Finest wine from Siena.', '1234567890', 'ed3cd853-341d-40a4-975b-78e15d862bca', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ed3cd853-341d-40a4-975b-78e15d862bca', 'Hendrik''s', 3499.99, 'Gin Hendrick`s - a unique high-quality gin premium-class, which is produced in small batches.', '1234567890', '5e659e17-f131-454b-80fc-17c53050282d', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


CREATE TABLE IF NOT EXISTS attributes(
                                         id VARCHAR(36),
                                         value VARCHAR NOT NULL,
                                         param_id VARCHAR(36) NOT NULL,
                                         product_id VARCHAR(36) NOT NULL,
                                         version INT,
                                         created_at TIMESTAMP,
                                         updated_at TIMESTAMP,
                                         PRIMARY KEY (id)
);

INSERT INTO attributes(id, value, param_id, product_id, version, created_at, updated_at) VALUES

('b4df60b0-d3f8-44f2-abbc-a63c923d23fa', 'Bitter', '72746941-51b7-48bf-85cf-a440386e9ae7','dd905ae3-3541-4809-8c06-387378b26420',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('eeeea497-efe7-47ba-a3a4-3cfe1b722bad', '0.500', '18f43c9c-f40c-4ad4-bd39-3302c28e3a2b','dd905ae3-3541-4809-8c06-387378b26420',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4e4f2152-f839-4151-9dca-416407260579', 'Lager', '57d26cf7-7035-468f-8e9f-00eecc5b2699','dd905ae3-3541-4809-8c06-387378b26420',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('ceaec33f-199f-4fb4-adef-8ecc7043b3ba', 'Smooth', '72746941-51b7-48bf-85cf-a440386e9ae7','f0c04d82-0649-45c5-a9ba-20cbbe423781',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('fdf08035-99f2-4be7-b417-6b707b95450b', '0.500', '18f43c9c-f40c-4ad4-bd39-3302c28e3a2b','f0c04d82-0649-45c5-a9ba-20cbbe423781',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('68b2d88c-fc59-457b-a016-b6e239a1666a', 'Stout', '57d26cf7-7035-468f-8e9f-00eecc5b2699','f0c04d82-0649-45c5-a9ba-20cbbe423781',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('2a3bf41b-0792-4fe9-83f1-83bb1d9a2f09', 'Rich and berrie', '72746941-51b7-48bf-85cf-a440386e9ae7','e4d7d687-bc29-478d-bd17-8e5e42fe5a1e',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('8d8367dd-9c4e-45ea-a99c-7222fd14e9e1', '0.750', '18f43c9c-f40c-4ad4-bd39-3302c28e3a2b','e4d7d687-bc29-478d-bd17-8e5e42fe5a1e',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('d0e93695-ee93-467a-b54a-fe4ba6d83451', 'Red wine', '57d26cf7-7035-468f-8e9f-00eecc5b2699','e4d7d687-bc29-478d-bd17-8e5e42fe5a1e',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('57f39fd2-850a-4514-ba42-aa467ec8139a', 'Extra Dry with juniper and cranberry flavour', '72746941-51b7-48bf-85cf-a440386e9ae7','ed3cd853-341d-40a4-975b-78e15d862bca',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('5ba02485-6dea-4c3d-9466-72d9eb8cef8b', '0.700', '18f43c9c-f40c-4ad4-bd39-3302c28e3a2b','ed3cd853-341d-40a4-975b-78e15d862bca',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('f3dc1ac4-b072-47a4-8109-8e66071a8940', 'Extra Dry', '57d26cf7-7035-468f-8e9f-00eecc5b2699','ed3cd853-341d-40a4-975b-78e15d862bca',  0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS inventories(
                                          id VARCHAR(36),
                                          qty INT NOT NULL DEFAULT 0,
                                          product_id VARCHAR(36) NOT NULL,
                                          version INT,
                                          created_at TIMESTAMP,
                                          updated_at TIMESTAMP,
                                          PRIMARY KEY (id)
);

INSERT INTO inventories(id, qty, product_id, version, created_at, updated_at) VALUES
('25afa45d-2702-4ea3-b9ee-d9e758752c6e', 35, 'dd905ae3-3541-4809-8c06-387378b26420', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('5873b12b-818d-4bf3-b473-91368ce80817', 20 , 'f0c04d82-0649-45c5-a9ba-20cbbe423781', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('969a1e61-356e-4f9b-b996-b66239620046', 12, 'e4d7d687-bc29-478d-bd17-8e5e42fe5a1e', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1f1fe42d-fa40-488d-8a5a-aba8ffe5531f', 9, 'ed3cd853-341d-40a4-975b-78e15d862bca', 0,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
