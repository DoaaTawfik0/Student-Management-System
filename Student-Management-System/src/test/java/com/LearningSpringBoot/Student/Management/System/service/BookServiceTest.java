package com.LearningSpringBoot.Student.Management.System.service;

import com.LearningSpringBoot.Student.Management.System.entity.Book;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import com.LearningSpringBoot.Student.Management.System.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;


    @Test
    void getAllBooksTest() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_FoundTest() {
        Book book = new Book();
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1);

        assertNotNull(result);
        verify(bookRepository).findById(1);
    }

    @Test
    void addBookTest() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertEquals(book, result);
    }

    @Test
    void updateBook_FoundTest() {
        Book existing = new Book();
        existing.setBookTitle("Old Title");

        Book updated = new Book();
        updated.setBookTitle("New Title");

        when(bookRepository.findById(1)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any())).thenReturn(existing);

        Book result = bookService.updateBook(1, updated);

        assertEquals("New Title", result.getBookTitle());
    }

    @Test
    void testDeleteBook_NotFound_ShouldThrowException() {
        when(bookRepository.findById(999)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookService.deleteBook(999);
        });

        assertEquals("Book not found with id: 999", exception.getMessage());
    }
}
