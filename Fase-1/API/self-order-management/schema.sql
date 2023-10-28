CREATE TABLE IF NOT EXISTS product
(
    product_number INTEGER NOT NULL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_type VARCHAR(255) NOT NULL,
    product_cost_price NUMERIC(15,2) NOT NULL,
    product_description VARCHAR(255),
    product_min_sub_item INTEGER,
    product_max_sub_item INTEGER,
    product_category VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS stock
(
    stock_product_number INTEGER NOT NULL PRIMARY KEY,
    stock_quantity BIGINT NOT NULL,
    stock_unit VARCHAR(255) NOT NULL,
    CONSTRAINT fk_stock_product_id FOREIGN KEY(stock_product_number) REFERENCES product(product_number) 
);

CREATE TABLE IF NOT EXISTS product_sub_item
(
    product_sub_item_id SERIAL PRIMARY KEY,
    product_sub_item_product_id_parent INTEGER NOT NULL,
    product_sub_item_product_id_sub INTEGER NOT NULL,
    CONSTRAINT fk_product_sub_item_product_id_parent FOREIGN KEY(product_sub_item_product_id_parent) REFERENCES product(product_number),
    CONSTRAINT fk_product_sub_item_product_id_sub FOREIGN KEY(product_sub_item_product_id_sub) REFERENCES product(product_number)
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
    order_number SERIAL PRIMARY KEY,
    order_date DATE NOT NULL,
    order_customer_nickname VARCHAR(255) NOT NULL,
    order_customer_document VARCHAR(20),
    order_status VARCHAR(255) NOT NULL,
    order_total NUMERIC(15,2) NOT NULL,
    CONSTRAINT fk_order_customer_id FOREIGN KEY(order_customer_document) REFERENCES customer(customer_document)
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id SERIAL PRIMARY KEY,
    order_item_product_number INTEGER NOT NULL,
    order_item_order_number SERIAL NOT NULL,
    CONSTRAINT fk_order_item_product_id FOREIGN KEY(order_item_product_number) REFERENCES product(product_number),
    CONSTRAINT fk_order_item_order_id FOREIGN KEY(order_item_order_number) REFERENCES "order"(order_number)
);

CREATE TABLE IF NOT EXISTS payment
(
    payment_order_number SERIAL PRIMARY KEY,
    payment_external_id UUID NOT NULL,
    payment_created_at TIMESTAMP NOT NULL,
    payment_status TEXT NOT NULL,
    payment_status_changed_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_payment_order_id FOREIGN KEY(payment_order_number) REFERENCES "order"(order_number)
)
