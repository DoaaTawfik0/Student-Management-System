package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Course {

    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, message = "name of course must have at least 3 characters !!")
    private String name;

    @Size(min = 10, message = "name of course must have at least 10 characters !!")
    private String description;
}
