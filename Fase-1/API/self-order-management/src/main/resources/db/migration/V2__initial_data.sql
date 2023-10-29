INSERT INTO inputs (input_number, input_name)
VALUES  (1, 'refrigerante coca-cola lata 355ml'),
        (2, 'refrigerante guarana antartica lata 355ml');

INSERT INTO stock (stock_input_number, stock_quantity)
VALUES  (2, 10),
        (1, 14);

INSERT INTO product (product_number, product_name, product_type, product_cost_price, product_description, product_min_sub_item, product_max_sub_item, product_category)
VALUES  (1, 'Refri coca-cola', 'DRINK', 7.00, 'Refrigerante coca-cola lata com 355ml', 0, 2147483647, 'REFRIGERANTE'),
        (3, 'REFRIGERANTE', 'DRINK', 0.00, 'Item que representa os refrigentes', 0, 2147483647, 'REFRIGERANTE');

INSERT INTO product_sub_item (product_sub_item_id, product_sub_item_product_id_parent, product_sub_item_product_id_sub)
VALUES  (1, 1, 3);

INSERT INTO inputs_product (inputs_product_number, inputs_product_input_number)
VALUES  (1, 1);
