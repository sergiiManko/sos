package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
