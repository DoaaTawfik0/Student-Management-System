package com.LearningSpringBoot.Student.Management.System.service;

import com.LearningSpringBoot.Student.Management.System.entity.Book;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import org.springframework.stereotype.Service;
import com.LearningSpringBoot.Student.Management.System.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }


    public Book addBook(Book Book) {
        return bookRepository.save(Book);
    }

    public Book updateBook(int id, Book updatedBook) {
        Book existingBook = checkBookExist(id);

        existingBook.setBookTitle(updatedBook.getBookTitle());
        existingBook.setBookAuthor(updatedBook.getBookAuthor());
        existingBook.setStudent(updatedBook.getStudent());

        /* apply updates by saving new data */
        bookRepository.save(existingBook);

        return existingBook;
    }

    public void deleteBook(int id) {
        checkBookExist(id);

        bookRepository.deleteById(id);
    }

    public void deleteBookFromStudent(int studentId, int bookId) {
        Book book = bookRepository.findByIdAndStudentId(bookId, studentId)
                .orElseThrow(() -> new NotFoundException("Book not found or doesn't belong to student"));

        book.setStudent(null); // Detach book from student
        bookRepository.save(book); // Persist the change
    }

    private Book checkBookExist(int id) {
        Book existingBook = getBookById(id);
        if (existingBook == null)
            throw new NotFoundException("Book not found with id: " + id);

        return existingBook;
    }

}
