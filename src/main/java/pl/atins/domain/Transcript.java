package pl.atins.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transcript",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id","semester","academic_year"})
        })
public class Transcript extends BaseEntity {

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Integer academicYear;

    @Column(name = "grade_point_average")
    private Double gradePointAverage;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    @OneToMany(mappedBy = "transcript", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setTranscript(this);
        recalculateGPA();
    }

    private void recalculateGPA() {
        if (grades.isEmpty()) {
            this.gradePointAverage = 0.0;
            return;
        }

        double sum = grades.stream()
                .mapToDouble(Grade::getScore)
                .sum();
        this.gradePointAverage = sum / grades.size();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transcript that = (Transcript) o;
        return Objects.equals(semester, that.semester) && Objects.equals(academicYear, that.academicYear) && Objects.equals(gradePointAverage, that.gradePointAverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), semester, academicYear, gradePointAverage);
    }
}
