package entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonPropertyOrder({"id", "name", "age", "email", "courses", "books"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student {

    @Id
    @GeneratedValue
    private int studentId;

    @Size(min = 3, message = "Name of student must have at least 3 characters !!")
    private String studentName;

    @Email(message = "Email must have a valid domain !!")
    private String studentEmail;

    @Size(min = 10, max = 23, message = "Age of Student must be between 10 & 23 !!")
    private int studentAge;

    // 'student' refers to the field in Course , applying cascading
    @OneToMany(mappedBy = "student")
    private List<Book> studentBooks;

    @ManyToMany()
    @JoinTable(
            name = "student_course", // Join table name
            joinColumns = @JoinColumn(name = "student_id"), // FK for Student
            inverseJoinColumns = @JoinColumn(name = "course_id") // FK for Course
    )
    private Set<Course> studentCourses = new HashSet<>();
}
