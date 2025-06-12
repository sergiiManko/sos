package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Student;
import pl.atins.domain.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findBySubjectAndStatus(Subject subject, String status);

    List<Enrollment> findBySubjectAndStatusOrderByEnrollmentDateAsc(Subject subject, String status);

    List<Enrollment> findByStudent(Student student);

    Optional<Enrollment> findByStudentAndSubject(Student student, Subject subject);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.subject.id = :subjectId AND e.status = :status")
    long countBySubjectIdAndStatus(@Param("subjectId") Long subjectId, @Param("status") String status);
}

