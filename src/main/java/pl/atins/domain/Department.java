package pl.atins.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "head_of_department", nullable = false, length = 100)
    private String headOfDepartment;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 10, unique = true)
    private String code;

    @OneToMany(mappedBy = "department")
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Teacher> teachers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Department that = (Department) o;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, name);
    }

    @Override
    public String toString() {
        return "Department{" + "code='" + code + '\'' + ", name='" + name + '\'' + ", headOfDepartment='" + headOfDepartment + '\'' + ", location='" + location + '\'' + '}';
    }
}
