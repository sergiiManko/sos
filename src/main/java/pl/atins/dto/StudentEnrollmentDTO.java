package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Grade;
import pl.atins.domain.Student;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollmentDTO {
    private Long enrollmentId;
    private Long studentId;
    private String studentFullName;
    private String studentNumber;
    private String faculty;
    private String specialization;
    private LocalDate enrollmentDate;
    private String status;
    private Long gradeId;
    private Double gradeValue;
    private Boolean hasGrade;

    public static StudentEnrollmentDTO fromEntities(Enrollment enrollment, Student student, Boolean hasGrade) {
        if (enrollment == null || student == null) {
            return null;
        }

        Grade currentGrade = student
                .getTranscript()
                .getGrades()
                .stream()
                .filter(grade -> grade.getEnrollment().getId().equals(enrollment.getId()))
                .findFirst()
                .orElse(null);
        return StudentEnrollmentDTO.builder()
                .enrollmentId(enrollment.getId())
                .studentId(student.getId())
                .studentFullName(student.getFirstName() + " " + student.getLastName())
                .studentNumber(student.getStudentNumber())
                .faculty(student.getFaculty())
                .specialization(student.getSpecialization())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .status(enrollment.getStatus())
                .gradeId(currentGrade != null ? currentGrade.getId() : null)
                .gradeValue(currentGrade != null ? currentGrade.getScore() : 0.0)
                .hasGrade(hasGrade)
                .build();
    }
}
