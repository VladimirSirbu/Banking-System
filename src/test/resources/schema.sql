DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS transaction;

create table if not exists customer
(
    id         bigint auto_increment primary key,
    email      varchar(255) null UNIQUE,
    first_name varchar(255) null,
    last_name  varchar(255) null
);

create table if not exists address
(
    customer_id bigint not null primary key,
    city        varchar(255) null,
    country     varchar(255) null,
    postal_code varchar(255) null,
    street      varchar(255) null,
    foreign key (customer_id) references customer (id)
);

create table if not exists account
(
    balance        double                      null,
    customer_id    bigint                      null,
    id             bigint auto_increment primary key,
    account_number varchar(255)                null UNIQUE,
    account_type   enum ('CHECKING', 'SAVING') null,
    state          enum ('ACTIVE', 'INACTIVE') null,
    foreign key (customer_id) references customer (id)
);

create table if not exists transaction
(
    amount           double                       null,
    account_id       bigint                       null,
    created_at       datetime(6)                  null,
    id               bigint auto_increment primary key,
    transaction_type enum ('DEPOSIT', 'WITHDRAW') null,
    foreign key (account_id) references account (id)
);







