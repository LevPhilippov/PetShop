DROP TABLE IF EXISTS order_details;
CREATE TABLE order_details (
                               id bigserial,
                               first_name VARCHAR(255)  NOT NULL,
                               last_name VARCHAR(255),
                               email VARCHAR(255) NOT NULL,
                               phone VARCHAR(20),
                               PRIMARY KEY (id)
);


DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
    id bigserial,
    user_id VARCHAR(36),
    price DECIMAL NOT NULL,
    order_status VARCHAR(20),
    details_id BIGINT NOT NULL,
    version INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT orders_details_fk FOREIGN KEY (details_id) REFERENCES order_details(id)
);

DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items (
                             id                    VARCHAR(36) NOT NULL,
                             product_id            VARCHAR(36) NOT NULL,
                             qty                   INT NOT NULL,
                             order_id              BIGINT NOT NULL,
                             PRIMARY KEY (id),
                             CONSTRAINT orderitems_orders_fk
                                 FOREIGN KEY (order_id)
                                    REFERENCES orders(id),
                             CONSTRAINT  orderitems_products_fk
                                FOREIGN KEY (product_id)
                                    REFERENCES products(id)

);


