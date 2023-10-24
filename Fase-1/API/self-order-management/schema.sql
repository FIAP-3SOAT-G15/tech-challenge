CREATE TABLE IF NOT EXISTS item
(
    item_name VARCHAR(255) NOT NULL PRIMARY KEY,
    item_type VARCHAR(255) NOT NULL,
    item_cost_price NUMERIC(15,2) NOT NULL,
    item_description VARCHAR(255),
    item_category VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS stock
(
    stock_item_name VARCHAR(255) PRIMARY KEY,
    stock_quantity BIGINT NOT NULL,
    stock_unit VARCHAR(255) NOT NULL,
    CONSTRAINT fk_stock_item_id FOREIGN KEY(stock_item_name) REFERENCES item(item_name)
);

CREATE TABLE IF NOT EXISTS sub_item
(
    sub_item_id SERIAL PRIMARY KEY,
    sub_item_item_id_parent VARCHAR(255) NOT NULL,
    sub_item_item_id_sub VARCHAR(255) NOT NULL,
    CONSTRAINT fk_sub_item_item_id_parent FOREIGN KEY(sub_item_item_id_parent) REFERENCES item(item_name),
    CONSTRAINT fk_sub_item_item_id_sub FOREIGN KEY(sub_item_item_id_sub) REFERENCES item(item_name)
);

CREATE TABLE IF NOT EXISTS customer
(
    customer_document VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(255) NOT NULL,
    customer_address VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS "order"
(
    order_id SERIAL PRIMARY KEY,
    order_total NUMERIC(15,2) NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    order_customer_document VARCHAR(20),
    order_customer_nickname VARCHAR(255) NOT NULL,
    CONSTRAINT fk_order_customer_id FOREIGN KEY(order_customer_document) REFERENCES customer(customer_document)
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id SERIAL PRIMARY KEY,
    order_item_item_name VARCHAR(255) NOT NULL,
    order_item_order_id SERIAL NOT NULL,
    CONSTRAINT fk_order_item_item_id FOREIGN KEY(order_item_item_name) REFERENCES item(item_name),
    CONSTRAINT fk_order_item_order_id FOREIGN KEY(order_item_order_id) REFERENCES "order"(order_id)
);
