CREATE TABLE ord.orders (
  id           varchar(36) PRIMARY KEY,
  created_at   timestamptz NOT NULL,
  id_account   varchar(36) NOT NULL,
  total        decimal(12,2) NOT NULL
);

CREATE TABLE ord.order_item (
  id          varchar(36) PRIMARY KEY,
 order_id      varchar(36) NOT NULL REFERENCES ord.orders(id) ON DELETE CASCADE,
  id_product    varchar(36) NOT NULL, 
  product_name  varchar(255) NOT NULL, 
  product_price decimal(12,2) NOT NULL,  
  product_unit  varchar(10) NOT NULL,  
  quantity      integer NOT NULL,
  total         decimal(12,2) NOT NULL 
);