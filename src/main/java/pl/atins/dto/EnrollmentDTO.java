package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Enrollment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long id;
    private LocalDate enrollmentDate;
    private String status;
    private SubjectDTO subject;
    private Long studentId;

    public static EnrollmentDTO fromEntity(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }

        return EnrollmentDTO.builder()
                .id(enrollment.getId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .status(enrollment.getStatus())
                .subject(SubjectDTO.fromEntity(enrollment.getSubject()))
                .studentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null)
                .build();
    }

    public static List<EnrollmentDTO> fromEntities(List<Enrollment> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            return new ArrayList<>();
        }

        return enrollments.stream()
                .map(EnrollmentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
