package pl.atins.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeFormDTO {

    @NotNull(message = "Score is required")
    @DecimalMin(value = "1.0", message = "Score must be at least 1.0")
    @DecimalMax(value = "5.0", message = "Score cannot be more than 5.0")
    private Double score;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;

    @NotNull(message = "Enrollment ID is required")
    private Long enrollmentId;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}
