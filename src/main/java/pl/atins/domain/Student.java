package pl.atins.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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
    private String agreementNum;
    private Double avgScore;
    private Integer currentSemester;
    private LocalDate dateGraduation;
    private Integer enrollmentYear;
    private Integer enrollSemester;
    private String faculty;
    private String modeOfStudy;
    private Boolean scholarshipHolder;
    private String specialization;

    @Column(unique = true)
    private String studentNumber;

    private String titleOfGrade;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Loan> loans = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Transcript transcript;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(studentNumber, student.studentNumber) && Objects.equals(faculty, student.faculty) && Objects.equals(specialization, student.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentNumber, faculty, specialization);
    }

    @Override
    public String toString() {
        return "Student{" +
                "agreementNum='" + agreementNum + '\'' +
                ", avgScore=" + avgScore +
                ", currentSemester=" + currentSemester +
                ", dateGraduation=" + dateGraduation +
                ", enrollmentYear=" + enrollmentYear +
                ", enrollSemester=" + enrollSemester +
                ", faculty='" + faculty + '\'' +
                ", modeOfStudy='" + modeOfStudy + '\'' +
                ", scholarshipHolder=" + scholarshipHolder +
                ", specialization='" + specialization + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", titleOfGrade='" + titleOfGrade + '\'' +
                ", department=" + department +
                '}';
    }
}
