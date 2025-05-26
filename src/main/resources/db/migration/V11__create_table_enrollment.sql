create table enrollment
(
    id              bigserial primary key,
    enrollment_date date        not null,
    status          varchar(20) not null,
    subject_id      bigint      not null,
    student_id      bigint      not null,
    created_at      timestamp   not null default current_timestamp,
    updated_at      timestamp   not null default current_timestamp,

    constraint fk_enrollment_subject foreign key (subject_id) references subject (id),
    constraint fk_enrollment_student foreign key (student_id) references student (id)
);

create index idx_enrollment_composite on enrollment (student_id, subject_id, status);
create index idx_enrollment_date on enrollment (enrollment_date);

