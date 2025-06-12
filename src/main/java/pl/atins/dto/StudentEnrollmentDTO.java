package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Enrollment;
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

    public static StudentEnrollmentDTO fromEntities(Enrollment enrollment, Student student) {
        if (enrollment == null || student == null) {
            return null;
        }

        return StudentEnrollmentDTO.builder()
                .enrollmentId(enrollment.getId())
                .studentId(student.getId())
                .studentFullName(student.getFirstName() + " " + student.getLastName())
                .studentNumber(student.getStudentNumber())
                .faculty(student.getFaculty())
                .specialization(student.getSpecialization())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .status(enrollment.getStatus())
                .build();
    }
}
