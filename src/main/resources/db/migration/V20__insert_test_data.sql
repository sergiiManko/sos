-- Insert test data that must be deleted before deploy to production
INSERT INTO department (id, name, code, head_of_department, location, created_at, updated_at)
VALUES (1000, 'Computer Science', 'CS', 'Dr. Alan Turing', 'Building A, Room 101', now(), now()),
       (1001, 'Mathematics', 'MATH', 'Dr. Ada Lovelace', 'Building B, Room 202', now(), now()),
       (1002, 'Physics', 'PHYS', 'Dr. Richard Feynman', 'Building C, Room 303', now(), now())
ON CONFLICT DO NOTHING;

INSERT INTO teacher (id, degree, employment_type, hire_date, office_number, title, department_id)
VALUES (2, 'Ph.D.', 'Full-time', '2015-09-01', 'A-101', 'Professor', 1000)
ON CONFLICT DO NOTHING;

INSERT INTO student (id, student_number, faculty, specialization, current_semester, enrollment_year,
                     enroll_semester, mode_of_study, scholarship_holder, title_of_grade,
                     date_graduation, avg_score, agreement_num, department_id)
VALUES (1, 'S001234', 'Computer Science', 'Software Engineering', 3, 2022, 1, 'Full-time', true, 'Bachelor',
        '2026-06-30', 4.5, 'AGR-001', 1000)
ON CONFLICT DO NOTHING;

INSERT INTO admin (id, description)
VALUES (3, 'Admin User')
ON CONFLICT DO NOTHING;

-- Insert test subjects
INSERT INTO subject (id, name, description, type, created_at, updated_at)
VALUES (4000, 'Introduction to Programming', 'Fundamentals of programming using Java', 'LECTURE', now(), now()),
       (4001, 'Data Structures and Algorithms', 'Study of data structures and algorithms', 'LECTURE', now(), now()),
       (4002, 'Calculus I', 'Introduction to differential and integral calculus', 'LECTURE', now(), now()),
       (4003, 'Linear Algebra', 'Study of vector spaces and linear transformations', 'LECTURE', now(), now()),
       (4004, 'Quantum Mechanics', 'Introduction to quantum theory', 'LECTURE', now(), now()),
       (4005, 'Classical Mechanics', 'Study of motion and physical interactions', 'LECTURE', now(), now())
ON CONFLICT DO NOTHING;

-- Link teachers to subjects
INSERT INTO teacher_subject (teacher_id, subject_id)
VALUES (2, 4000),
       (2, 4001),
       (2, 4003)
ON CONFLICT DO NOTHING;

INSERT INTO enrollment (id, enrollment_date, status, subject_id, student_id, created_at, updated_at)
VALUES (1, '2023-09-01', 'ENROLLED', 4000, 1, now(), now()),   -- Alice is enrolled in Intro to Programming
       (2, '2023-09-02', 'WAITLISTED', 4004, 1, now(), now()), -- Alice is waitlisted for Quantum Mechanics
       (3, '2023-09-03', 'DROPPED', 4002, 1, now(), now())     -- Alice dropped Calculus I
ON CONFLICT DO NOTHING;

-- Insert test payment data
INSERT INTO payment (id, amount, payment_date, payment_method, reference_code, status, title, created_at, updated_at)
VALUES (1000, 1500.00, '2023-09-01 10:15:30', 'CREDIT_CARD', 'PAY-2023-001', 'PAID', 'Tuition Fee - Fall Semester', now(), now()),
       (1001, 300.00, '2023-09-05 14:22:10', 'BANK_TRANSFER', 'PAY-2023-002', 'PAID', 'Laboratory Fee - Chemistry', now(), now()),
       (1002, 200.50, '2023-09-10 09:45:00', 'PAYPAL', 'PAY-2023-003', 'PAID', 'Books and Materials', now(), now()),
       (1003, 750.00, '2023-10-01 11:30:15', 'CREDIT_CARD', 'PAY-2023-004', 'PAID', 'Dormitory Fee - October', now(), now()),
       (1004, 50.00, '2023-10-15 16:00:00', 'CASH', 'PAY-2023-005', 'PAID', 'Student ID Replacement', now(), now()),
       (1005, 1500.00, '2023-11-01 10:00:00', 'BANK_TRANSFER', 'PAY-2023-006', 'PENDING', 'Tuition Fee - Winter Semester', now(), now()),
       (1006, 450.00, '2023-11-05 13:20:45', 'CREDIT_CARD', 'PAY-2023-007', 'FAILED', 'Study Trip Payment', now(), now()),
       (1007, 120.00, '2023-11-10 09:10:30', 'PAYPAL', 'PAY-2023-008', 'PAID', 'Extra Curricular Activities', now(), now()),
       (1008, 750.00, '2023-12-01 10:45:00', 'BANK_TRANSFER', 'PAY-2023-009', 'PAID', 'Dormitory Fee - December', now(), now()),
       (1009, 85.50, '2023-12-12 15:30:00', 'CASH', 'PAY-2023-010', 'PAID', 'Exam Retake Fee', now(), now()),
       (1010, 350.00, '2024-01-05 11:20:00', 'CREDIT_CARD', 'PAY-2024-001', 'PENDING', 'Laboratory Fee - Physics', now(), now()),
       (1011, 1200.00, '2024-01-10 14:00:15', 'BANK_TRANSFER', 'PAY-2024-002', 'PAID', 'Thesis Review Fee', now(), now()),
       (1012, 200.00, '2024-01-20 09:30:00', 'PAYPAL', 'PAY-2024-003', 'REFUNDED', 'Workshop Fee - Canceled', now(), now()),
       (1013, 750.00, '2024-02-01 10:15:30', 'CREDIT_CARD', 'PAY-2024-004', 'PAID', 'Dormitory Fee - February', now(), now()),
       (1014, 1500.00, '2024-02-15 13:00:00', 'BANK_TRANSFER', 'PAY-2024-005', 'PENDING', 'Tuition Fee - Spring Semester', now(), now())
ON CONFLICT DO NOTHING;

-- Set the sequence values to be higher than our manually inserted IDs to avoid conflicts
SELECT setval('department_id_seq', 2000, false);
SELECT setval('users_id_seq', 4000, false);
SELECT setval('subject_id_seq', 5000, false);
SELECT setval('enrollment_id_seq', 6000, false);
SELECT setval('payment_id_seq', 2000, false);

insert into transcript (semester, academic_year, grade_point_average, student_id)
values (1, 2023, 4.5, 1);

insert into grade (score, comments, grade_date, teacher_id, enrollment_id, transcript_id)
values (4.5, 'excellent work on the final project', '2023-02-15', 2, 1, 1);

insert into grade (score, comments, grade_date, teacher_id, enrollment_id, transcript_id)
values (5.0, 'perfect score on all assignments', '2023-02-20', 2, 2, 1);

insert into book(title, author, isbn, category, status)
values ('Clean code', 'Robert C. Martin', '978-0132350884', 'Programming', 'returned'),
       ('Introduction to Algorithms', 'Thomas H. Cormen', '978-0262033848', 'Algorithms', 'in use'),
       ('The Art of Computer Programming', 'Donald E. Knuth', '978-0201896831', 'Computer Science', 'returned');

insert into loan(due_date, loan_date, return_date, status, student_id, book_id)
values ('2024-10-01', '2024-09-01', '2024-09-15', 'returned', 1, 1),
       ('2025-09-01', '2025-07-01', null, 'in use', 1, 2),
       ('2023-12-01', '2023-11-01', '2023-11-20', 'returned', 1, 3);