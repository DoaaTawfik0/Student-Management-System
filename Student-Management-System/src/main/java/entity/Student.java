package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Student {

    @Id
    @GeneratedValue
    private int studentId;

    @Size(min = 3, message = "Name of student must have at least 3 characters !!")
    private String studentName;

    @Email(message = "Email must have a valid domain")
    private String studentEmail;

    @Size(min = 10, max = 23, message = "Age of Student must be between 10 & 23 !!")
    private int studentAge;



}
