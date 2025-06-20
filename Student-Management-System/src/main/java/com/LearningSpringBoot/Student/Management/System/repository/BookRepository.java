package com.LearningSpringBoot.Student.Management.System.repository;

import com.LearningSpringBoot.Student.Management.System.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIdAndStudentId(int bookId, int studentId);
}
