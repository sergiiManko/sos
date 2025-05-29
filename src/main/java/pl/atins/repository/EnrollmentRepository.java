package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Student;
import pl.atins.domain.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);

    Optional<Enrollment> findByStudentAndSubject(Student student, Subject subject);
}
