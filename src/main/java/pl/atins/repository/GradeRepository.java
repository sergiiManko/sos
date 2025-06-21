package pl.atins.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Grade;
import pl.atins.domain.Student;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Page<Grade> findByTranscriptStudent(Student student, Pageable pageable);
    Optional<Grade> findByEnrollmentId(Long enrollmentId);
}
