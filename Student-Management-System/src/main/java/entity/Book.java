package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
