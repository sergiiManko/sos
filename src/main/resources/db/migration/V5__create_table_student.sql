-- Create student table
create table if not exists student
(
    id                 bigint      not null,
    agreement_num      varchar(50),
    avg_score          double precision,
    current_semester   int,
    date_graduation    date,
    enrollment_year    int,
    enroll_semester    int,
    faculty            varchar(70) not null,
    mode_of_study      varchar(50) not null,
    scholarship_holder boolean     not null,
    specialization     varchar(70) not null,
    student_number     varchar(70),
    title_of_grade     varchar(50) not null,
    created_at         timestamp   not null default now(),
    updated_at         timestamp   not null default now(),

    constraint pk_student primary key (id),
    constraint fk_student_user foreign key (id) references users (id) on delete cascade,
    constraint uq_student unique (student_number)
);
