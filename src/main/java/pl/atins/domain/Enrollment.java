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
@Table(name = "enrollment")
public class Enrollment extends BaseEntity {

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(nullable = false, length = 20)
    private String status;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentDate, that.enrollmentDate) &&
               Objects.equals(subject, that.subject) &&
               Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), enrollmentDate, subject, student);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + getId() +
                ", enrollmentDate=" + enrollmentDate +
                ", status='" + status + '\'' +
                ", subject=" + (subject != null ? subject.getName() : null) +
                ", student=" + (student != null ? student.getFullName() : null) +
                '}';
    }
}
