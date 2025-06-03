package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Grade;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Long id;
    private Double score;
    private String comments;
    private LocalDate gradeDate;
    private TeacherDTO teacher;
    private Long enrollmentId;
    private String subjectName;
    private Long transcriptId;

    public static GradeDTO fromEntity(Grade grade) {
        if (grade == null) {
            return null;
        }

        return GradeDTO.builder()
                .id(grade.getId())
                .score(grade.getScore())
                .comments(grade.getComments())
                .gradeDate(grade.getGradeDate())
                .teacher(new TeacherDTO(
                        grade.getTeacher().getId(),
                        grade.getTeacher().getFirstName(),
                        grade.getTeacher().getLastName(),
                        grade.getTeacher().getDegree()))
                .enrollmentId(grade.getEnrollment().getId())
                .subjectName(grade.getEnrollment().getSubject().getName())
                .transcriptId(grade.getTranscript().getId())
                .build();
    }
}
