package pl.atins.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher")
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    
    @Column(nullable = false, length = 50)
    private String degree;
    
    @Column(name = "employment_type", nullable = false, length = 50)
    private String employmentType;
    
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    
    @Column(name = "office_number", nullable = false, length = 20)
    private String officeNumber;
    
    @Column(nullable = false, length = 100)
    private String title;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(degree, teacher.degree) &&
               Objects.equals(title, teacher.title) &&
               Objects.equals(officeNumber, teacher.officeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), degree, title, officeNumber);
    }
}
