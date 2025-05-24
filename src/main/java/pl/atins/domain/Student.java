package pl.atins.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")

public class Student extends User {
    private int agreementNum;
    private double avgScore;
    private int currentSemester;
    private LocalDateTime dataGraduation;
    private int enrollmentYear;
    private int enrollSemester;
    private String faculity;
    private String modeOfStudy;
    private boolean scholarshipHolder;
    private String specialization;
    private int studentNumber;
    private String titleOfGrade;
}