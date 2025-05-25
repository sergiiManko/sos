package pl.atins.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Loan> loans = new HashSet<>();

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

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", faculty='" + faculty + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
