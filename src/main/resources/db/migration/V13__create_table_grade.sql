create table if not exists grade
(
    id            bigserial primary key,
    score         double precision not null,
    comments      varchar(500),
    grade_date    date             not null,
    teacher_id    bigint           not null,
    enrollment_id bigint           not null,
    transcript_id bigint           not null,
    created_at    timestamp        not null default now(),
    updated_at    timestamp        not null default now(),

    constraint fk_grade_teacher foreign key (teacher_id) references teacher (id),
    constraint fk_grade_enrollment foreign key (enrollment_id) references enrollment (id) on delete cascade,
    constraint fk_grade_transcript foreign key (transcript_id) references transcript (id) on delete cascade
);