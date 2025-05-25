-- Create book table
create table if not exists book
(
    id         bigserial    not null,
    author     varchar(255) not null,
    category   varchar(100) not null,
    isbn       varchar(20)  not null,
    status     varchar(50)  not null,
    title      varchar(255) not null,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now(),
    
    constraint pk_book primary key (id),
    constraint uq_book_isbn unique (isbn)
);
