package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Subject;
import pl.atins.domain.Teacher;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s JOIN s.teachers t WHERE t = :teacher")
    List<Subject> findByTeacher(@Param("teacher") Teacher teacher);

}

