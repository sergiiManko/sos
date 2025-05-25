-- Create teacher table
create table if not exists teacher
(
    id              bigint       not null,
    degree          varchar(50)  not null,
    employment_type varchar(50)  not null,
    hire_date       date         not null,
    office_number   varchar(20)  not null,
    title           varchar(100) not null,

    constraint pk_teacher primary key (id),
    constraint fk_teacher_user foreign key (id) references users (id)
);
