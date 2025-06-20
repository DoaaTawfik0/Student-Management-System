package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, message = "name of course must have at least 3 characters !!")
    private String name;

    @Size(min = 10, message = "name of course must have at least 10 characters !!")
    private String description;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @Size(min = 3, message = "name of course must have at least 3 characters !!") String getName() {
        return name;
    }

    public void setName(@Size(min = 3, message = "name of course must have at least 3 characters !!") String name) {
        this.name = name;
    }

    public @Size(min = 10, message = "name of course must have at least 10 characters !!") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "name of course must have at least 10 characters !!") String description) {
        this.description = description;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
