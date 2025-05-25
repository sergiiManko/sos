package pl.atins.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan")
public class Loan extends BaseEntity {

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Loan loan = (Loan) o;
        return Objects.equals(loanDate, loan.loanDate) &&
                Objects.equals(student, loan.student) &&
                Objects.equals(book, loan.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loanDate, student, book);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", student=" + (student != null ? student.getStudentNumber() : "null") +
                ", book=" + (book != null ? book.getIsbn() : "null") +
                '}';
    }
}
