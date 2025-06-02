create table if not exists transcript
(
    id                  bigserial primary key,
    semester            int       not null,
    academic_year       int       not null,
    grade_point_average double precision,
    student_id          bigint    not null,
    created_at          timestamp not null default now(),
    updated_at          timestamp not null default now(),

    constraint fk_transcript_student foreign key (student_id) references student (id) on delete cascade
);
