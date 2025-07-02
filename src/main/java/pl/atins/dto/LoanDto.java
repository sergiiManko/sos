package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Loan;

import java.time.LocalDate;

/**
 * @author Serhii Manko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LoanDto(Loan loan) {
        this.id = loan.getId();
        this.bookTitle = loan.getBook().getTitle();
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.returnDate = loan.getReturnDate();
    }
}
