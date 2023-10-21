

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
    stock_qtde BIGINT NOT NULL,
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

CREATE TABLE IF NOT EXISTS client
(
    client_document VARCHAR(20) PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL,
    client_email VARCHAR(255) NOT NULL,
    client_phone VARCHAR(255) NOT NULL,
    client_address VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id SERIAL PRIMARY KEY,
    order_total NUMERIC(15,2) NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    order_client_document VARCHAR(20),
    order_client_nick_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_order_client_id FOREIGN KEY(order_client_document) REFERENCES client(client_document)
);

CREATE TABLE IF NOT EXISTS orders_item
(
    orders_item_id SERIAL PRIMARY KEY,
    orders_item_item_name VARCHAR(255) NOT NULL,
    orders_item_order_id SERIAL NOT NULL,
    CONSTRAINT fk_orders_item_item_id FOREIGN KEY(orders_item_item_name) REFERENCES item(item_name),
    CONSTRAINT fk_orders_item_order_id FOREIGN KEY(orders_item_order_id) REFERENCES orders(order_id)
);