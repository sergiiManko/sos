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
       (2, 4001)
ON CONFLICT DO NOTHING;

INSERT INTO enrollment (id, enrollment_date, status, subject_id, student_id, created_at, updated_at)
VALUES (1, '2023-09-01', 'ENROLLED', 4000, 1, now(), now()),   -- Alice is enrolled in Intro to Programming
       (2, '2023-09-02', 'WAITLISTED', 4004, 1, now(), now()), -- Alice is waitlisted for Quantum Mechanics
       (3, '2023-09-03', 'DROPPED', 4002, 1, now(), now())     -- Alice dropped Calculus I
ON CONFLICT DO NOTHING;

-- Set the sequence values to be higher than our manually inserted IDs to avoid conflicts
SELECT setval('department_id_seq', 2000, false);
SELECT setval('users_id_seq', 4000, false);
SELECT setval('subject_id_seq', 5000, false);
SELECT setval('enrollment_id_seq', 6000, false);
