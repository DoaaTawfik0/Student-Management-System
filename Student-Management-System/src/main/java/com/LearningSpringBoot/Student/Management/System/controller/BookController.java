package com.LearningSpringBoot.Student.Management.System.controller;

import com.LearningSpringBoot.Student.Management.System.dto.ApiResponse;
import com.LearningSpringBoot.Student.Management.System.entity.Book;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.LearningSpringBoot.Student.Management.System.service.BookService;
import com.LearningSpringBoot.Student.Management.System.service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    private StudentService studentService;


    @GetMapping("")
    public ApiResponse<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ApiResponse<>(books.size(), books);
    }

    @GetMapping("/pagination/{pageNumber}/{pageSize}")
    public ApiResponse<Page<Book>> paginateBooks(@PathVariable int pageNumber, @PathVariable int pageSize) {
        Page<Book> booksWithPagination = bookService.getBooksWithPagination(pageNumber, pageSize);

        return new ApiResponse<>(booksWithPagination.getSize(), booksWithPagination);
    }

    @GetMapping("/sorting/{field}")
    public ApiResponse<List<Book>> sortBooks(@PathVariable String field) {
        List<Book> booksWithSorting = bookService.getBooksWithSortingUponSomeField(field);

        return new ApiResponse<>(booksWithSorting.size(), booksWithSorting);
    }

    @GetMapping("/paginationAndSorting/{field}/{pageNumber}/{pageSize}")
    public ApiResponse<Page<Book>> sortAndPaginateBooks(@PathVariable String field, @PathVariable int pageNumber, @PathVariable int pageSize) {
        Page<Book> booksWithSortingAndPagination = bookService.getBooksWithSortingAndPagination(field, pageNumber, pageSize);

        return new ApiResponse<>(booksWithSortingAndPagination.getSize(), booksWithSortingAndPagination);
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
        Book existingbook = bookService.getBookById(book.getId());
        if (existingbook != null)
            return ResponseEntity.status(409).build();

        Book savedbook = bookService.addBook(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{bookId}").buildAndExpand(savedbook.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBookById(@PathVariable int bookId, @Valid @RequestBody Book updatedBook) {
        Book savedBook = bookService.updateBook(bookId, updatedBook);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(savedBook.getId()).toUri();
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
