package pl.atins.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class StudentRegistrationDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Faculty is required")
    private String faculty;

    @NotBlank(message = "Mode of study is required")
    private String modeOfStudy;

    @NotNull(message = "Scholarship holder status is required")
    private Boolean scholarshipHolder;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Title of grade is required")
    private String titleOfGrade;

    @NotNull(message = "Department is required")
    private Long departmentId;
}
