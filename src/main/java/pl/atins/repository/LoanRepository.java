package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Loan;

import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStudentId(Long studentId);

    List<Loan> findByStatus(String status);

}
