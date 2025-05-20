create table if not exists payment
(
    id             bigserial      not null,
    amount         numeric(10, 2) not null,
    payment_date   timestamp      not null,
    payment_method varchar(30)    not null,
    reference_code varchar(50)    not null,
    status         varchar(20)    not null,
    title          varchar(100)   not null,
    created_at     timestamp      not null default now(),
    updated_at     timestamp      not null default now(),
    constraint pk_payment primary key (id),
    constraint uq_payment unique (reference_code)
);