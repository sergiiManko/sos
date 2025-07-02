package pl.atins.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.dto.LoanDto;
import pl.atins.repository.LoanRepository;
import pl.atins.service.LoanService;

import java.util.List;

/**
 * @author Serhii Manko
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public List<LoanDto> getLoansForStudent(Long studentId) {
        return loanRepository.findByStudentId(studentId).stream()
                .map(LoanDto::new)
                .toList();
    }

    @Override
    public long countLoansByStatus(Long studentId, String status) {
        return loanRepository.findByStudentId(studentId).stream()
                .filter(loan -> loan.getStatus().equalsIgnoreCase(status))
                .count();
    }
}