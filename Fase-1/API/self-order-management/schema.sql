CREATE TABLE IF NOT EXISTS product
(
    product_number SERIAL NOT NULL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_type VARCHAR(255) NOT NULL,
    product_cost_price NUMERIC(15,2) NOT NULL,
    product_description VARCHAR(255),
    product_min_sub_item INTEGER,
    product_max_sub_item INTEGER,
    product_category VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS inputs (
    input_number SERIAL NOT NULL PRIMARY KEY,
    input_name VARCHAR(2000) NOT NULL
);

CREATE TABLE IF NOT EXISTS inputs_product (
    inputs_product_number SERIAL NOT NULL,
    inputs_product_input_number SERIAL NOT NULL,
    CONSTRAINT pk_inputs_product PRIMARY KEY(inputs_product_number, inputs_product_input_number),
    CONSTRAINT fk_inputs_product_number FOREIGN KEY(inputs_product_number) REFERENCES product(product_number),
    CONSTRAINT fk_inputs_product_input_number FOREIGN KEY(inputs_product_input_number) REFERENCES inputs(input_number)
);

CREATE TABLE IF NOT EXISTS stock
(
    stock_input_number SERIAL NOT NULL PRIMARY KEY,
    stock_quantity BIGINT NOT NULL,
    CONSTRAINT fk_stock_input_number FOREIGN KEY(stock_input_number) REFERENCES inputs(input_number)
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
);

insert into inputs (input_number, input_name)
values  (1, 'refrigerante coca-cola lata 355ml'),
        (2, 'refrigerante guarana antartica lata 355ml');

insert into stock (stock_input_number, stock_quantity)
values  (2, 10),
        (1, 14);

insert into product (product_number, product_name, product_type, product_cost_price, product_description, product_min_sub_item, product_max_sub_item, product_category)
values  (1, 'Refri coca-cola', 'DRINK', 7.00, 'Refrigerante coca-cola lata com 355ml', 0, 2147483647, 'REFRIGERANTE'),
        (3, 'REFRIGERANTE', 'DRINK', 0.00, 'Item que representa os refrigentes', 0, 2147483647, 'REFRIGERANTE');

insert into product_sub_item (product_sub_item_id, product_sub_item_product_id_parent, product_sub_item_product_id_sub)
values  (1, 1, 3);

insert into inputs_product (inputs_product_number, inputs_product_input_number)
values  (1, 1);