package com.LearningSpringBoot.Student.Management.System.serviceTest;

import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import com.LearningSpringBoot.Student.Management.System.repository.CourseRepository;
import com.LearningSpringBoot.Student.Management.System.repository.StudentRepository;
import com.LearningSpringBoot.Student.Management.System.service.StudentService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student(1, "John Doe", "john@example.com", 20);
        course = new Course(101, "Math", "Math Course");

        student.setStudentCourses(new HashSet<>());
        course.setStudents(new HashSet<>());
    }

    @Test
    public void getAllStudentsTest() {
        // Arrange
        List<Student> mockStudents = Arrays.asList(
                student,
                new Student(2, "Jane Smith", "jane@example.com", 22)
        );

        Mockito.when(studentRepository.findAll()).thenReturn(mockStudents);

        // Act
        List<Student> result = studentService.getAllStudents();

        // Assert
        assertEquals(2, result.size(), "Expected 2 students returned");
        assertEquals("John Doe", result.get(0).getStudentName());
        assertEquals("Jane Smith", result.get(1).getStudentName());
        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getStudentByIdTest() {
        // Arrange
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        // Act
        Student result = studentService.getStudentById(1);

        // Assert
        assertEquals("John Doe", result.getStudentName(), "Student name should be 'John Doe'");
        assertNotNull(result, "Student should not be null");
        assertEquals(1, result.getId(), "Student ID should be 1");
    }

    @Test
    public void addStudentTest() {
        // Arrange
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        // Act
        Student result = studentService.addStudent(student);

        // Assert
        assertEquals("John Doe", result.getStudentName());
        assertEquals("john@example.com", result.getStudentEmail());
        assertNotNull(result);
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.eq(student));
    }

    @Test
    public void deleteStudentTest() {
        // Arrange
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        // Act
        studentService.deleteStudent(1);

        // Assert
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(Mockito.eq(1));
    }

    @Test
    public void deleteStudent_NotFound_ShouldThrowException() {
        // Arrange
        Mockito.when(studentRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            studentService.deleteStudent(999);
        });

        assertEquals("Student not found with id: 999", ex.getMessage());
    }

    @Test
    public void testAssignStudentToCourse() {
        // Arrange
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        // Act
        studentService.assignStudentToCourse(student);

        // Assert
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    public void testDeleteStudentFromCourse() {
        // Arrange
        student.getStudentCourses().add(course);
        course.getStudents().add(student);

        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(101)).thenReturn(Optional.of(course));

        // Act
        studentService.deleteStudentFromCourse(1, 101);

        // Assert
        assertFalse(student.getStudentCourses().contains(course));
        assertFalse(course.getStudents().contains(student));
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    public void deleteStudentFromCourse_StudentNotFound_ShouldThrowException() {
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            studentService.deleteStudentFromCourse(1, 101);
        });

        assertEquals("Student not found with ID: 1", ex.getMessage());
    }

    @Test
    public void deleteStudentFromCourse_CourseNotFound_ShouldThrowException() {
        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(101)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            studentService.deleteStudentFromCourse(1, 101);
        });

        assertEquals("Course not found with ID: 101", ex.getMessage());
    }
}
