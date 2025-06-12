package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Transcript;

import java.util.List;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {

    List<Transcript> findByStudentIdOrderBySemesterDesc(Long studentId);
}
