CREATE TABLE IF NOT EXISTS products (
    id serial,
    title varchar(255),
    price decimal,
    PRIMARY KEY(id)
);


INSERT INTO products(title,price) VALUES ('Korgi',30000), ('Double sabers', 7500), ('Axe', 11000),
                                         ('Sting', 21000), ('Stuff', 13000.27), ('Bow',11000),('Sword', 6000),('Mordor''s bow', 17000),
                                         ('Ring of power', 99999), ('Palantir', 30000), ('Crown of kings', 6000), ('Elven light', 21000),
                                         ('Ring of white sorcerer', 70000), ('Archenstone', 45000);


CREATE TABLE IF NOT EXISTS product_images (
              id bigserial,
              product_id bigint,
              filepath varchar(255),
              PRIMARY KEY(id),
              CONSTRAINT fk_products_product_images FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO product_images(product_id,filepath) VALUES (1,'pets/1/1'),(1,'pets/1/2');