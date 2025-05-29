package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {
    private Long id;
    private String name;
    private String description;
    private String type;
    private List<TeacherDTO> teachers;

    public static SubjectDTO fromEntity(Subject subject) {
        if (subject == null) {
            return null;
        }

        List<TeacherDTO> teacherDTOs = new ArrayList<>();
        if (subject.getTeachers() != null && !subject.getTeachers().isEmpty()) {
            teacherDTOs = subject.getTeachers().stream()
                    .map(teacher -> new TeacherDTO(
                            teacher.getId(),
                            teacher.getFirstName(),
                            teacher.getLastName(),
                            teacher.getDegree()))
                    .collect(Collectors.toList());
        }

        return SubjectDTO.builder()
                .id(subject.getId())
                .name(subject.getName())
                .description(subject.getDescription())
                .type(subject.getType())
                .teachers(teacherDTOs)
                .build();
    }
}
