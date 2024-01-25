INSERT INTO customer (id, first_name, last_name, email)
VALUES (1, 'John', 'Doe', 'john.doe@gmail.com'),
       (2, 'Alice', 'Smith', 'alice.smith@gmail.com'),
       (3, 'Bob', 'Johnson', 'bob.johnson@gmail.com'),
       (4, 'Emily', 'Brown', 'emily.brown@gmail.com'),
       (5, 'Sarah', 'Davis', 'sarah.davis@gmail.com');

INSERT INTO address (customer_id, street, city, postal_code, country)
VALUES (1, 'Wall Street 12', 'New York', '12345', 'USA'),
       (2, 'Main Street 45', 'Los Angeles', '67890', 'USA'),
       (3, 'High Street 7', 'London', '065211', 'UK'),
       (4, 'Rue de la Paix 3', 'Paris', '75002', 'France'),
       (5, 'Alexanderplatz 10', 'Berlin', '10178', 'Germany');

INSERT INTO account (id, account_number, balance, state, account_type, customer_id)
VALUES (101, 'RO53RZBR9323762774847299', '1000.0', 'ACTIVE', 'CHECKING', 1),
       (102, 'RO17RZBR1821836351937654', '100.0', 'ACTIVE', 'CHECKING', 2),
       (103, 'RO66PORL8163465575485292', '60.0', 'ACTIVE', 'SAVING', 3),
       (104, 'RO60PORL8345426166863865', '230.0', 'ACTIVE', 'SAVING', 4),
       (105, 'RO05PORL1745736626875269', '500.0', 'INACTIVE', 'CHECKING', 5);