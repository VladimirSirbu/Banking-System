CREATE TABLE customer
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255) UNICODE,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE address
(
    customer_id BIGINT AUTO_INCREMENT,
    street      VARCHAR(255),
    city        VARCHAR(255),
    postal_code VARCHAR(20),
    country     VARCHAR(255),
#     customer_id BIGINT,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;

CREATE TABLE account
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    account_number VARCHAR(24) UNIQUE,
    balance        NUMERIC,
    state          VARCHAR(20),
    account_type   VARCHAR(50),
    customer_id    BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    PRIMARY KEY (id)
) ENGINE = InnoDB;

INSERT INTO customer (id, first_name, last_name, email)
VALUES (1, 'John', 'Doe', 'john.doe@example.com'),
       (2, 'Jane', 'Smith', 'jane.smith@example.com'),
       (3, 'Robert', 'Johnson', 'robert.johnson@example.com'),
       (4, 'Alice', 'Williams', 'alice.williams@example.com'),
       (5, 'Michael', 'Brown', 'michael.brown@example.com'),
       (6, 'Emily', 'Jones', 'emily.jones@example.com'),
       (7, 'William', 'Davis', 'william.davis@example.com'),
       (8, 'Sarah', 'Miller', 'sarah.miller@example.com'),
       (9, 'James', 'Wilson', 'james.wilson@example.com'),
       (10, 'Olivia', 'Taylor', 'olivia.taylor@example.com');

INSERT INTO address (address.customer_id, street, city, postal_code, country)
VALUES (1, '123 Main Street', 'New York', '10001', 'USA'),
       (2, '456 Elm Avenue', 'Los Angeles', '90001', 'USA'),
       (3, '789 Oak Lane', 'Chicago', '60601', 'USA'),
       (4, '987 Pine Road', 'San Francisco', '94101', 'USA'),
       (5, '321 Cedar Drive', 'Miami', '33101', 'USA'),
       (6, '654 Birch Street', 'Houston', '77001', 'USA'),
       (7, '876 Redwood Lane', 'Dallas', '75201', 'USA'),
       (8, '543 Maple Road', 'Seattle', '98101', 'USA'),
       (9, '234 Spruce Avenue', 'Boston', '02101', 'USA'),
       (10, '789 Willow Lane', 'Philadelphia', '19101', 'USA');


INSERT INTO account (id, account_number, balance, state, account_type, customer_id)
VALUES (1001, 'RO53RZBR9323762774847299', '1000.0', 'ACTIVE', 'CHECKING', 1),
       (1002, 'RO17RZBR1821836351937654', '100.0', 'ACTIVE', 'CHECKING', 2),
       (1003, 'RO60PORL8345426166863865', '230.0', 'ACTIVE', 'SAVING', 4),
       (1004, 'RO05PORL1745736626875269', '500.0', 'INACTIVE', 'CHECKING', 5),
       (1005, 'RO71PORL5522944396468932', '721.0', 'ACTIVE', 'SAVING', 6),
       (1006, 'RO66PORL8163465575485292', '60.0', 'ACTIVE', 'SAVING', 7);


