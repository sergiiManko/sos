-- Create admin table
create table if not exists admin
(
    id          bigint    not null,
    description varchar(250),
    created_at  timestamp not null default now(),
    updated_at  timestamp not null default now(),

    constraint pk_admin primary key (id),
    constraint fk_admin_user foreign key (id) references users (id) on delete cascade
);
