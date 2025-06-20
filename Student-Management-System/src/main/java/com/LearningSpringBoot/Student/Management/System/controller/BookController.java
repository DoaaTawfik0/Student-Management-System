package controller;

import entity.Book;
import entity.Student;
import exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.BookService;
import service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    private StudentService studentService;

    public BookController(BookService bookService, StudentService studentService) {
        this.bookService = bookService;
        this.studentService = studentService;
    }

    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable int bookId) {
        Book book = bookService.getBookById(bookId);
        if (book == null)
            throw new NotFoundException("Book not found with id: " + bookId);

        return book;
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book existingbook = bookService.getBookById(book.getBookId());
        if (existingbook != null)
            return ResponseEntity.status(409).build();

        Book savedbook = bookService.addBook(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{bookId}").buildAndExpand(savedbook.getBookId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBookById(@PathVariable int bookId, @Valid @RequestBody Book updatedBook) {
        Book savedBook = bookService.updateBook(bookId, updatedBook);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(savedBook.getBookId()).toUri();
        return ResponseEntity.ok().location(location).build();
    }


    @DeleteMapping("/{bookId}")
    public ResponseEntity<Student> deleteBook(@PathVariable int bookId) {
        bookService.deleteBook(bookId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/Books").build().toUri();
        return ResponseEntity.ok().location(location).build();
    }


    @GetMapping("/{bookId}/owner")
    public Student getOwnerOfBook(@PathVariable int bookId) {

        Book existedBook = bookService.getBookById(bookId);
        if (existedBook == null) {
            throw new NotFoundException("Book not found with id: " + bookId);
        }
        return studentService.getStudentById(existedBook.getStudent().getId());
    }
}
