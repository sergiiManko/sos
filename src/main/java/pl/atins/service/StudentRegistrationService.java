package pl.atins.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.Department;
import pl.atins.domain.Role;
import pl.atins.domain.Student;
import pl.atins.dto.StudentRegistrationDTO;
import pl.atins.exception.DepartmentNotFoundException;
import pl.atins.exception.EmailExistException;
import pl.atins.exception.RoleNotFoundException;
import pl.atins.repository.DepartmentRepository;
import pl.atins.repository.RoleRepository;
import pl.atins.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentRegistrationService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(StudentRegistrationDTO registrationDTO) {
        if (studentRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new EmailExistException(String.format("Email: %s is already in use", registrationDTO.getEmail()));
        }

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RoleNotFoundException("Student role: ROLE_STUDENT not found"));

        Department department = departmentRepository.findById(registrationDTO.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(
                        String.format("Department with id: %s not found", registrationDTO.getDepartmentId())));

        Student student = new Student();

        // Fields related to User
        student.setFirstName(registrationDTO.getFirstName());
        student.setLastName(registrationDTO.getLastName());
        student.setEmail(registrationDTO.getEmail());
        student.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        student.setRole(studentRole);
        student.setEnabled(true);
        student.setAccountNonLocked(true);
        student.setFailedAttempt(0);

        // Fields related to Student
        student.setFaculty(registrationDTO.getFaculty());
        student.setModeOfStudy(registrationDTO.getModeOfStudy());
        student.setScholarshipHolder(registrationDTO.getScholarshipHolder());
        student.setSpecialization(registrationDTO.getSpecialization());
        student.setTitleOfGrade(registrationDTO.getTitleOfGrade());
        student.setDepartment(department);

        studentRepository.save(student);
    }
}
