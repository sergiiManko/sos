package pl.atins.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Grade;
import pl.atins.domain.Student;
import pl.atins.domain.Teacher;
import pl.atins.domain.Transcript;
import pl.atins.dto.GradeDTO;
import pl.atins.dto.GradeFormDTO;
import pl.atins.repository.EnrollmentRepository;
import pl.atins.repository.GradeRepository;
import pl.atins.repository.TeacherRepository;
import pl.atins.repository.TranscriptRepository;
import pl.atins.service.GradeService;
import pl.atins.service.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final TeacherRepository teacherRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TranscriptRepository transcriptRepository;

    @Override
    public Page<GradeDTO> getStudentGrades(Long studentId, Pageable pageable) {
        Student student = studentService.findById(studentId);
        Page<Grade> grades = gradeRepository.findByTranscriptStudent(student, pageable);
        return grades.map(GradeDTO::fromEntity);
    }

    @Override
    public GradeDTO getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Grade not found with ID: " + id));

        return GradeDTO.fromEntity(grade);
    }

    @Override
    @Transactional
    public void addGrade(GradeFormDTO gradeFormDTO, Long teacherId) {
        if (gradeFormDTO.getScore() < 1.0 || gradeFormDTO.getScore() > 5.0) {
            throw new IllegalArgumentException("Grade score must be between 1.0 and 5.0");
        }

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with ID: " + teacherId));

        Student student = studentService.findById(gradeFormDTO.getStudentId());

        Enrollment enrollment = enrollmentRepository.findById(gradeFormDTO.getEnrollmentId())
                .orElseThrow(() -> new NoSuchElementException("Enrollment not found with ID: " + gradeFormDTO.getEnrollmentId()));

        if (!enrollment.getStudent().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Enrollment does not belong to the specified student");
        }

        if (!enrollment.getStatus().equals(Enrollment.STATUS_ENROLLED)) {
            throw new IllegalArgumentException("Cannot add grade to a non-enrolled student");
        }

        List<Transcript> transcripts = transcriptRepository.findByStudentIdOrderBySemesterDesc(student.getId());
        Transcript transcript;

        if (transcripts.isEmpty()) {
            transcript = new Transcript();
            transcript.setStudent(student);
            transcript.setSemester(student.getCurrentSemester() != null ? student.getCurrentSemester() : 1);
            transcript.setAcademicYear(LocalDate.now().getYear());
            transcript.setGradePointAverage(0.0);
            transcript = transcriptRepository.save(transcript);
        } else {
            transcript = transcripts.getFirst();
        }

        Grade grade = new Grade();
        grade.setScore(gradeFormDTO.getScore());
        grade.setComments(gradeFormDTO.getComments());
        grade.setGradeDate(LocalDate.now());
        grade.setTeacher(teacher);
        grade.setEnrollment(enrollment);
        grade.setTranscript(transcript);

        Grade savedGrade = gradeRepository.save(grade);

        transcript.addGrade(savedGrade);
        transcriptRepository.save(transcript);

    }
}
