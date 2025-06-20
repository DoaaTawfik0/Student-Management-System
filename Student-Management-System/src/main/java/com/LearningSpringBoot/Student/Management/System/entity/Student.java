package entity;

import entity.Book;
import entity.Course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@JsonPropertyOrder({"id", "name", "email", "dateOfBirth", "courses", "books"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Student {


    @Id
    @GeneratedValue
    private int id;


    @Size(min = 3, message = "name must have more than 3 characters!!")
    private String name;


    @NotBlank
    @Email(message = "Invalid Email Format")
    private String email;

    @Past
    private LocalDate dateOfBirth;

    // 'student' refers to the field in Course , applying cascading
    @OneToMany(mappedBy = "student")
    private List<Book> books;

    @ManyToMany()
    @JoinTable(
            name = "student_course", // Join table name
            joinColumns = @JoinColumn(name = "student_id"), // FK for Student
            inverseJoinColumns = @JoinColumn(name = "course_id") // FK for Course
    )
    private Set<Course> courses = new HashSet<>();


    public Student() {
    }

    public Student(int id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("studentId")
    public int getId() {
        return id;
    }

    @JsonProperty("studentName")
    public @Size(min = 3, message = "name must have more than 3 characters!!") String getName() {
        return name;
    }

    @JsonProperty("studentEmail")
    public @NotBlank @Email(message = "Invalid Email Format") String getEmail() {
        return email;
    }

    @JsonProperty("studentDateOfBirth")
    public @Past LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@Size(min = 3, message = "name must have more than 3 characters!!") String name) {
        this.name = name;
    }

    public void setEmail(@NotBlank @Email(message = "Invalid Email Format") String email) {
        this.email = email;
    }

    public void setDateOfBirth(@Past LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", books=" + books +
                '}';
    }
}