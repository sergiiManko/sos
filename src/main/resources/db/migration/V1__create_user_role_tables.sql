create table if not exists role
(
    id         bigserial   not null,
    name       varchar(30) not null,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now(),
    constraint pk_role primary key (id),
    constraint uq_role unique (name)
);

create table if not exists users
(
    id                 bigserial    not null,
    first_name         varchar(100) not null,
    last_name          varchar(100) not null,
    email              varchar(50)  not null,
    password           varchar(256) not null,
    lock_time          timestamp,
    enabled            boolean               default true,
    account_non_locked boolean               default true,
    failed_attempt     integer               default 0,
    created_at         timestamp    not null default now(),
    updated_at         timestamp    not null default now(),
    role_id            bigint,
    constraint pk_user primary key (id),
    constraint fk_user foreign key (role_id) references role (id)
);

create index idx_users on users (role_id);
