package pl.atins.service;

import pl.atins.dto.LoanDto;

import java.util.List;

/**
 * @author Serhii Manko
 */
public interface LoanService {
    List<LoanDto> getLoansForStudent(Long studentId);

    long countLoansByStatus(Long studentId, String status);
}
