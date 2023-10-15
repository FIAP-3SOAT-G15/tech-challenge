CREATE DATABASE selforderdb;


CREATE TABLE IF NOT EXISTS item
(
    item_id SERIAL PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_type VARCHAR(255) NOT NULL,
    item_description VARCHAR(255),
    item_type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS stock
(
    stock_id SERIAL PRIMARY KEY,
    stock_item_id SERIAL NOT NULL,
    stock_qtde BIGINT NOT NULL,
    stock_unit VARCHAR(255) NOT NULL,
    CONSTRAINT fk_stock_item_id FOREIGN KEY(stock_item_id) REFERENCES item(item_id)
);

CREATE TABLE IF NOT EXISTS sub_item
(
    sub_item_id SERIAL PRIMARY KEY,
    sub_item_item_id_parent SERIAL NOT NULL,
    sub_item_item_id_sub SERIAL NOT NULL,
    CONSTRAINT fk_sub_item_item_id_parent FOREIGN KEY(sub_item_item_id_parent) REFERENCES item(item_id),
    CONSTRAINT fk_sub_item_item_id_sub FOREIGN KEY(sub_item_item_id_sub) REFERENCES item(item_id)
);