package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Book {

    @Id
    @GeneratedValue
    private int bookId;

    @Size(min = 6, message = "Title of book must have at at least 6 characters !!")
    private String bookTitle;

    @Size(min = 6, message = "Author name must have at least 6 characters !!")
    private String bookAuthor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore //Skipping the field in JSON
    @JoinColumn(name = "student_id", nullable = true) // This creates a foreign key column in the Book table
    private Student student;
}
