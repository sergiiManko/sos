create table subject
(
    id            bigserial primary key,
    name          varchar(100) not null unique,
    description   varchar(100) not null,
    type          varchar(50)  not null,
    created_at    timestamp    not null default current_timestamp,
    updated_at    timestamp    not null default current_timestamp
);

create table teacher_subject
(
    teacher_id bigint not null,
    subject_id bigint not null,
    primary key (teacher_id, subject_id),
    constraint fk_teacher_subject_teacher foreign key (teacher_id) references teacher (id),
    constraint fk_teacher_subject_subject foreign key (subject_id) references subject (id)
);
