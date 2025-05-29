package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
