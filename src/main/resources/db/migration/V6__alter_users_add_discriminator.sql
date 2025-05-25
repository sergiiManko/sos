-- Add user_type discriminator column to users table
alter table users
    add column if not exists user_type varchar(31);

-- Set existing users to STUDENT type if they have a corresponding entry in student table
update users u
set user_type = 'STUDENT'
where exists (select 1
              from student s
              where s.id = u.id);

-- Set any remaining users without a type to a default
update users
set user_type = 'ADMIN'
where user_type is null;

-- Make user_type NOT NULL after setting values
alter table users
    alter column user_type set not null;

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
