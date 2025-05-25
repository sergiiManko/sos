-- Create department table
create table if not exists department
(
    id                 bigserial    not null,
    head_of_department varchar(100) not null,
    location           varchar(100) not null,
    name               varchar(100) not null,
    code               varchar(10)  not null,
    created_at         timestamp    not null default now(),
    updated_at         timestamp    not null default now(),

    constraint pk_department primary key (id),
    constraint uq_department_name unique (name),
    constraint uq_department_code unique (code)
);

alter table student
    add column if not exists department_id bigint;

alter table student
    add constraint fk_student_department
        foreign key (department_id) references department (id);

alter table teacher
    add column if not exists department_id bigint;

alter table teacher
    add constraint fk_teacher_department
        foreign key (department_id) references department (id);
