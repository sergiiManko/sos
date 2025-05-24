create table if not exists student
(
    id                 bigserial             not null,
    agreement_num      serial                not null,
    avg_score          double precision      not null,
    current_semester   int                   not null,
    data_graduation    LocalDateTime         not null,
    enrollment_year    int                   not null,
    enroll_semester    int                   not null,
    faculity           varchar(70)           not null,
    mode_of_study      varchar(50)           not null,
    scholarship_holder boolean               not null,
    specialization     varchar(70)           not null,
    student_number     serial                not null,
    title_of_grade     varchar(50)           not null,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now(),

    constraint pk_student primary key (id),
    constraint uq_student unique (student_number)
);