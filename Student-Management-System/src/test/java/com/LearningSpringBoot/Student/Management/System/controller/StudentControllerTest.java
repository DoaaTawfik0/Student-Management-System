package com.LearningSpringBoot.Student.Management.System.controller;

import com.LearningSpringBoot.Student.Management.System.entity.Book;
import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.service.BookService;
import com.LearningSpringBoot.Student.Management.System.service.CourseService;
import com.LearningSpringBoot.Student.Management.System.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;
    @MockitoBean
    private BookService bookService;
    @MockitoBean
    private CourseService courseService;

    private Student createStudent(int id) {
        return new Student(1, "Ali", "Ali@email.com", 20);
    }


    private Book createBook() {
        return new Book(1, "Book Title", "Author", null);
    }

    private Course createCourse() {
        return new Course(1, "Math", "MTH101");
    }

    @Test
    void getAllStudents_ShouldReturnStudentList() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(createStudent(1), createStudent(2)));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getStudentById_ValidId_ShouldReturnStudent() throws Exception {
        Student student = createStudent(1);
        when(studentService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Ali"));
    }

    @Test
    void getStudentById_InvalidId_ShouldReturn404() throws Exception {
        when(studentService.getStudentById(999)).thenReturn(null);

        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addStudent_NewStudent_ShouldReturnCreated() throws Exception {
        Student student = createStudent(1);
        when(studentService.getStudentById(1)).thenReturn(null);
        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());
    }

    @Test
    void addStudent_ExistingStudent_ShouldReturnConflict() throws Exception {
        Student student = createStudent(1);
        when(studentService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateStudent_ShouldReturnOk() throws Exception {
        Student updated = createStudent(1);
        when(studentService.updateStudent(eq(1), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentById_ValidId_ShouldReturnOk() throws Exception {
        Student student = createStudent(1);
        Book book = createBook();
        student.setStudentBooks(List.of(book));

        when(studentService.getStudentById(1)).thenReturn(student);
        doNothing().when(studentService).deleteStudent(1);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void assignBookToStudent_ShouldReturnOk() throws Exception {
        Student student = createStudent(1);
        Book book = createBook();

        when(studentService.getStudentById(1)).thenReturn(student);
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(post("/students/1/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBooksOfStudent_ShouldReturnBookList() throws Exception {
        Student student = createStudent(1);
        student.setStudentBooks(List.of(createBook()));

        when(studentService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(get("/students/1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void deleteBookFromStudent_ShouldReturnOk() throws Exception {
        Student student = createStudent(1);
        Book book = createBook();
        book.setStudent(student);

        when(studentService.getStudentById(1)).thenReturn(student);
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(delete("/students/1/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void enrollStudentInCourse_ShouldReturnOk() throws Exception {
        // Create properly initialized entities
        Student student = createStudent(1);
        Course course = createCourse();

        // Mock all necessary service calls
        when(studentService.getStudentById(1)).thenReturn(student);
        when(courseService.getCourseById(1)).thenReturn(course);
        when(studentService.assignStudentToCourse(eq(student))).thenReturn(student);

        // Perform test
        mockMvc.perform(post("/students/1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllCoursesOfStudent_ShouldReturnCourseList() throws Exception {
        Student student = createStudent(1);
        student.setStudentCourses(Set.of(createCourse()));

        when(studentService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(get("/students/1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void deleteStudentFromCourse_ShouldReturnOk() throws Exception {
        doNothing().when(studentService).deleteStudentFromCourse(1, 1);

        mockMvc.perform(delete("/students/1/courses/1"))
                .andExpect(status().isOk());
    }
}
