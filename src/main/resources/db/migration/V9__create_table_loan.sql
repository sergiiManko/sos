-- Create loan table
create table if not exists loan
(
    id          bigserial   not null,
    due_date    date        not null,
    loan_date   date        not null,
    return_date date,
    status      varchar(50) not null,
    student_id  bigint      not null,
    book_id     bigint      not null,
    created_at  timestamp   not null default now(),
    updated_at  timestamp   not null default now(),

    constraint pk_loan primary key (id),
    constraint fk_loan_student foreign key (student_id) references student (id),
    constraint fk_loan_book foreign key (book_id) references book (id)
);

-- Create index for frequent queries
create index idx_loan_status on loan (status);
create index idx_loan_due_date on loan (due_date);
create index idx_loan_student_id on loan (student_id);
create index idx_loan_book_id on loan (book_id);
