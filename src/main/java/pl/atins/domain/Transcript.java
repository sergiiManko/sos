package pl.atins.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transcript")
public class Transcript extends BaseEntity {

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Integer academicYear;

    @Column(name = "grade_point_average")
    private Double gradePointAverage;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToMany(mappedBy = "transcript", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades = new ArrayList<>();

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setTranscript(this);
        recalculateGPA();
    }

    public void removeGrade(Grade grade) {
        grades.remove(grade);
        grade.setTranscript(null);
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
}
