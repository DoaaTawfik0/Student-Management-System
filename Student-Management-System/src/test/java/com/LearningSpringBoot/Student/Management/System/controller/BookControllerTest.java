package com.LearningSpringBoot.Student.Management.System.controller;

import com.LearningSpringBoot.Student.Management.System.entity.Book;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.service.BookService;
import com.LearningSpringBoot.Student.Management.System.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private StudentService studentService;

    private Book createBook(int id, String title, Student owner) {
        return new Book(id, title, "Author", owner);
    }

    private Student createStudent() {
        return new Student(1, "Ali", "ali@gmail.com", 22);
    }

    @Test
    void getAllBooks_ShouldReturnBookList() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(
                createBook(1, "Book A", null),
                createBook(2, "Book B", null)
        ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getBookById_ExistingId_ShouldReturnBook() throws Exception {
        Book book = createBook(1, "Book A", null);
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookTitle").value("Book A"));
    }

    @Test
    void getBookById_NotFound_ShouldThrowException() throws Exception {
        when(bookService.getBookById(999)).thenReturn(null);

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_NewBook_ShouldReturnCreated() throws Exception {
        Book book = createBook(1, "New Book", null);
        when(bookService.getBookById(1)).thenReturn(null);
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    void addBook_Conflict_ShouldReturn409() throws Exception {
        Book book = createBook(1, "Duplicate Book", null);
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateBook_ShouldReturnOk() throws Exception {
        Book updatedBook = createBook(1, "Updated Book", null);
        when(bookService.updateBook(eq(1), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook_ShouldReturnOk() throws Exception {
        doNothing().when(bookService).deleteBook(1);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getBookOwner_ExistingBook_ShouldReturnStudent() throws Exception {
        Student student = createStudent();
        Book book = createBook(1, "Book Title", student);

        when(bookService.getBookById(1)).thenReturn(book);
        when(studentService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(get("/books/1/owner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Ali"));
    }

    @Test
    void getBookOwner_NotFoundBook_ShouldReturn404() throws Exception {
        when(bookService.getBookById(999)).thenReturn(null);

        mockMvc.perform(get("/books/999/owner"))
                .andExpect(status().isNotFound());
    }
}
