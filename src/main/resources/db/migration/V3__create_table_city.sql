create table if not exists city
(
    id         bigserial   not null,
    name       varchar(30) not null,
    code       varchar(30) not null,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now(),
    constraint pk_city primary key (id),
    constraint uq_city unique (code)
);