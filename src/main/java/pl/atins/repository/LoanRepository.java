package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Loan;
import pl.atins.domain.Student;

import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStudent(Student student);

    List<Loan> findByStatus(String status);

}
