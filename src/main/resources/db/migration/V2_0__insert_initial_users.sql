INSERT INTO role (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_TEACHER'),
       ('ROLE_STUDENT');

INSERT INTO users (first_name, last_name, email, password, role_id, user_type)
VALUES ('Jan', 'Kowalski', 'jan.kowalski@example.com', '$2a$10$ng4PAiMZ55jL8rjHTnXNDORGi1dTNPxX1NEzph/RUurVID7SanuN.', 3, 'STUDENT'),
       ('Maria', 'Nowak', 'maria.nowak@example.com', '$2a$10$3KO10/E7/T8q5wwL.luQa.RW76BZsslpYCl7S57K8LnNWL42AfQje', 2, 'TEACHER');

-- Password for Jan Kowalski: xt34Ws78
-- Password for Maria Nowak: pl4RwE02L