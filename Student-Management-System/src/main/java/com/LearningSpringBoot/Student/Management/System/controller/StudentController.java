package com.LearningSpringBoot.Student.Management.System.controller;

import com.LearningSpringBoot.Student.Management.System.dto.ApiResponse;
import com.LearningSpringBoot.Student.Management.System.entity.Book;
import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import com.LearningSpringBoot.Student.Management.System.service.BookService;
import com.LearningSpringBoot.Student.Management.System.service.CourseService;
import com.LearningSpringBoot.Student.Management.System.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private StudentService studentService;
    private BookService bookService;
    private CourseService courseService;

    @GetMapping("")
    public ApiResponse<List<Student>> getAllStudents() {
        List<Student> allStudents = studentService.getAllStudents();

        return new ApiResponse<>(allStudents.size(), allStudents);
    }

    @GetMapping("/pagination/{pageNumber}/{pageSize}")
    public ApiResponse<Page<Student>> paginateStudents(@PathVariable int pageNumber, @PathVariable int pageSize) {
        Page<Student> studentsWithPagination = studentService.getStudentsWithPagination(pageNumber, pageSize);

        return new ApiResponse<>(studentsWithPagination.getSize(), studentsWithPagination);
    }

    @GetMapping("/sorting/{field}")
    public ApiResponse<List<Student>> sortStudents(@PathVariable String field) {
        List<Student> studentsWithSorting = studentService.getStudentsWithSortingUponSomeField(field);

        return new ApiResponse<>(studentsWithSorting.size(), studentsWithSorting);
    }

    @GetMapping("/paginationAndSorting/{field}/{pageNumber}/{pageSize}")
    public ApiResponse<Page<Student>> sortAndPaginateStudents(@PathVariable String field, @PathVariable int pageNumber, @PathVariable int pageSize) {
        Page<Student> studentsWithSortingAndPagination = studentService.getStudentsWithSortingAndPagination(field, pageNumber, pageSize);

        return new ApiResponse<>(studentsWithSortingAndPagination.getSize(), studentsWithSortingAndPagination);
    }


    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        Student student = studentService.getStudentById(id);
        if (student == null)
            throw new NotFoundException("Student is not found with id: " + id);
        return student;
    }

    @PostMapping("")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student existingStudent = studentService.getStudentById(student.getId());
        if (existingStudent != null)
            return ResponseEntity.status(409).build();

        Student savedStudent = studentService.addStudent(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedStudent.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudentById(@PathVariable int studentId, @Valid @RequestBody Student updatedStudent) {
        Student savedStudent = studentService.updateStudent(studentId, updatedStudent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(savedStudent.getId()).toUri();
        return ResponseEntity.ok().location(location).build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable int studentId) {
        Student student = studentService.getStudentById(studentId);

        if (student == null)
            return ResponseEntity.notFound().build();

        //assign null to student fk in book table
        for (Book book : student.getStudentBooks()) {
            book.setStudent(null);
            bookService.addBook(book);
        }

        student.getStudentCourses().clear(); /* Remove all courses -> handle M:M (student_course table) */

        studentService.deleteStudent(studentId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/students").build().toUri();
        return ResponseEntity.ok().location(location).build();
    }

    @PostMapping("/{studentId}/books/{bookId}")
    public ResponseEntity<Student> assignBookToStudent(@PathVariable int studentId, @PathVariable int bookId) {
        Student existingStudent = studentService.getStudentById(studentId);
        Book existingBook = bookService.getBookById(bookId);

        if (existingStudent == null)
            throw new NotFoundException("Student is not found with Id: " + studentId);

        if (existingBook == null)
            throw new NotFoundException("Book is not found with Id: " + bookId);

        existingBook.setStudent(existingStudent);

        bookService.addBook(existingBook);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/students").build().toUri();
        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping("{studentId}/books")
    public List<Book> getAllBooksOfStudent(@PathVariable int studentId) {
        Student existingStudent = studentService.getStudentById(studentId);

        if (existingStudent == null)
            throw new NotFoundException("Student is not found with Id: " + studentId);

        return existingStudent.getStudentBooks();
    }

    @DeleteMapping("/{studentId}/books/{bookId}")
    public ResponseEntity<Student> deleteBookFromStudent(@PathVariable int studentId, @PathVariable int bookId) {
        Student existingStudent = studentService.getStudentById(studentId);
        Book existingBook = bookService.getBookById(bookId);

        if (existingStudent == null)
            throw new NotFoundException("Student is not found with Id: " + studentId);

        if (existingBook == null)
            throw new NotFoundException("Book is not found with Id: " + bookId);

        if (existingBook.getStudent() == null || existingBook.getStudent().getId() != studentId)
            throw new NotFoundException("Book does not belong to this student");

        existingBook.setStudent(null);
        bookService.deleteBookFromStudent(studentId, bookId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/students").build().toUri();
        return ResponseEntity.ok().location(location).build();
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> enrollStudentInCourse(@PathVariable int studentId, @PathVariable int courseId) {
        Student existingStudent = studentService.getStudentById(studentId);
        Course existingCourse = courseService.getCourseById(courseId);

        if (existingStudent == null)
            throw new NotFoundException("Student is not found with Id: " + studentId);

        if (existingCourse == null)
            throw new NotFoundException("Course is not found with Id: " + courseId);

        existingStudent.getStudentCourses().add(existingCourse);
        existingCourse.getStudents().add(existingStudent);

        studentService.assignStudentToCourse(existingStudent);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/students").build().toUri();
        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping("{studentId}/courses")
    public Set<Course> getAllCoursesOfStudent(@PathVariable int studentId) {
        Student existingStudent = studentService.getStudentById(studentId);

        if (existingStudent == null)
            throw new NotFoundException("Student is not found with Id: " + studentId);

        return existingStudent.getStudentCourses();
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> deleteStudentFromCourse(@PathVariable int studentId, @PathVariable int courseId) {
        studentService.deleteStudentFromCourse(studentId, courseId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/students").build().toUri();
        return ResponseEntity.ok().location(location).build();
    }


}
