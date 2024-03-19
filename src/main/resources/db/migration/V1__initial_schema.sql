CREATE TABLE IF NOT EXISTS product
(
    product_number SERIAL NOT NULL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_category VARCHAR(255) NOT NULL,
    product_price NUMERIC(15,2) NOT NULL,
    product_description VARCHAR(255),
    product_min_sub_item INTEGER NOT NULL,
    product_max_sub_item INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS component (
    component_number SERIAL NOT NULL PRIMARY KEY,
    component_name VARCHAR(2000) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_component (
    product_component_product_number SERIAL NOT NULL,
    product_component_component_number SERIAL NOT NULL,
    CONSTRAINT pk_product_component PRIMARY KEY(product_component_product_number, product_component_component_number),
    CONSTRAINT fk_product_component_product_number FOREIGN KEY(product_component_product_number) REFERENCES product(product_number) ON DELETE CASCADE,
    CONSTRAINT fk_product_component_component_number FOREIGN KEY(product_component_component_number) REFERENCES component(component_number) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS stock
(
    stock_component_number SERIAL NOT NULL PRIMARY KEY,
    stock_quantity BIGINT NOT NULL,
    CONSTRAINT fk_stock_component_number FOREIGN KEY(stock_component_number) REFERENCES component(component_number) ON DELETE CASCADE
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
    customer_id VARCHAR(36) PRIMARY KEY,
    customer_document VARCHAR(20),
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    customer_phone VARCHAR(255),
    customer_address VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS "order"
(
    order_number SERIAL PRIMARY KEY,
    order_date DATE NOT NULL,
    order_customer_id CHAR(36),
    order_status TEXT NOT NULL,
    order_total NUMERIC(15,2) NOT NULL,
    CONSTRAINT fk_order_customer_id FOREIGN KEY(order_customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id SERIAL PRIMARY KEY,
    order_item_product_number INTEGER NOT NULL,
    order_item_order_number SERIAL NOT NULL,
    CONSTRAINT fk_order_item_product_id FOREIGN KEY(order_item_product_number) REFERENCES product(product_number),
    CONSTRAINT fk_order_item_order_id FOREIGN KEY(order_item_order_number) REFERENCES "order"(order_number) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment
(
    payment_order_number SERIAL PRIMARY KEY,
    payment_external_order_id TEXT NOT NULL,
    payment_external_order_global_id TEXT,
    payment_payment_info TEXT NOT NULL,
    payment_created_at TIMESTAMP NOT NULL,
    payment_status TEXT NOT NULL,
    payment_status_changed_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_payment_order_id FOREIGN KEY(payment_order_number) REFERENCES "order"(order_number)
);
