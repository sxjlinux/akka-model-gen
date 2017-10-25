-----------------------------------------------------
-- This file is 100% ***GENERATED***, DO NOT EDIT! --
-----------------------------------------------------

CREATE TABLE product (
  product_id VARCHAR(64) NOT NULL,
  product_name VARCHAR(64) NOT NULL,
  product_unit VARCHAR(64) NOT NULL,
  unit_price DOUBLE NOT NULL,
  CONSTRAINT product_pk PRIMARY KEY(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE order (
  order_id VARCHAR(64) NOT NULL,
  CONSTRAINT order_pk PRIMARY KEY(order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE order_item (
  order_id VARCHAR(64) NOT NULL,
  product_id VARCHAR(64) NOT NULL,
  item_name VARCHAR(64),
  unit_price DOUBLE,
  order_quantity DOUBLE NOT NULL,
  CONSTRAINT order_item_pk PRIMARY KEY(order_id, product_id),
  CONSTRAINT order_item_fk FOREIGN KEY(order_id) REFERENCES order(order_id),
  CONSTRAINT order_item_product_fk FOREIGN KEY(product_id) REFERENCES product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

