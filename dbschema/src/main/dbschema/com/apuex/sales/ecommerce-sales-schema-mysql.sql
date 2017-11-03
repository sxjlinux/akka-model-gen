/*****************************************************
 ** This file is 100% ***GENERATED***, DO NOT EDIT! **
 *****************************************************/

DROP USER IF EXISTS 'sales'@'localhost';
DROP DATABASE IF EXISTS sales;

CREATE DATABASE sales DEFAULT CHARACTER SET 'UTF8' DEFAULT COLLATE utf8_unicode_ci;

CREATE USER 'sales'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'sales'@'localhost' WITH GRANT OPTION;

USE sales;

CREATE TABLE sales.product (
  product_id VARCHAR(64) NOT NULL,
  product_name VARCHAR(64) NOT NULL,
  product_unit VARCHAR(64) NOT NULL,
  unit_price DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sales.order (
  order_id VARCHAR(64) NOT NULL,
  order_time DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sales.order_item (
  order_id VARCHAR(64) NOT NULL,
  product_id VARCHAR(64) NOT NULL,
  item_name VARCHAR(64),
  unit_price DOUBLE,
  order_quantity DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE sales.product ADD CONSTRAINT product_pk PRIMARY KEY(product_id);
ALTER TABLE sales.order ADD CONSTRAINT order_pk PRIMARY KEY(order_id);
ALTER TABLE sales.order_item ADD CONSTRAINT order_item_pk PRIMARY KEY(order_id, product_id);

ALTER TABLE sales.order_item ADD CONSTRAINT order_item_fk FOREIGN KEY(order_id) REFERENCES sales.order(order_id);
ALTER TABLE sales.order_item ADD CONSTRAINT order_item_product_fk FOREIGN KEY(product_id) REFERENCES sales.product(product_id);

