package pl.atins.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@Table(name = "student")
@DiscriminatorValue("STUDENT")
public class Student extends User {
    private int agreementNum;
    private double avgScore;
    private int currentSemester;
    private LocalDate dateGraduation;
    private int enrollmentYear;
    private int enrollSemester;
    private String faculty;
    private String modeOfStudy;
    private boolean scholarshipHolder;
    private String specialization;

    @Column(unique = true)
    private String studentNumber;

    private String titleOfGrade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(studentNumber, student.studentNumber) &&
                Objects.equals(faculty, student.faculty) &&
                Objects.equals(specialization, student.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentNumber, faculty, specialization);
    }
}