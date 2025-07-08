alter table payment
    add column student_id bigint;
create index idx_payment_student_id on payment (student_id);

alter table payment
    add constraint fk_payment_student
        foreign key (student_id)
            references student (id)
            on update cascade
            on delete restrict;