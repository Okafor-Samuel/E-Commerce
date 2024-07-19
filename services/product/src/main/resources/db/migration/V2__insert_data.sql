-- Insert data into category table
INSERT INTO category (id, description, name)
VALUES (1, 'Electronics devices and gadgets', 'Electronics');

INSERT INTO category (id, description, name)
VALUES (2, 'Home and kitchen appliances', 'Home Appliances');

INSERT INTO category (id, description, name)
VALUES (3, 'Books and reading materials', 'Books');

INSERT INTO category (id, description, name)
VALUES (4, 'Clothing and fashion accessories', 'Fashion');


-- Insert data into product table
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (1, 'Latest model smartphone', 'Smartphone', 100, 699.99, 1);

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (2, '42 inch LED TV', 'LED TV', 50, 399.99, 1);

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (3, 'Stainless steel refrigerator', 'Refrigerator', 30, 499.99, 2);

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (4, 'Blender with multiple speed settings', 'Blender', 75, 79.99, 2);

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (5, 'Bestselling fiction novel', 'Fiction Novel', 200, 19.99, 3);

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (6, 'High-quality jeans', 'Jeans', 150, 49.99, 4);

INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES (7, 'Stylish leather jacket', 'Leather Jacket', 40, 99.99, 4);
