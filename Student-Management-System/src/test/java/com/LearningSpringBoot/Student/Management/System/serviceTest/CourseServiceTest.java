package com.LearningSpringBoot.Student.Management.System.serviceTest;

import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import com.LearningSpringBoot.Student.Management.System.repository.CourseRepository;
import com.LearningSpringBoot.Student.Management.System.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void getAllCoursesTest() {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertEquals(2, result.size());
    }

    @Test
    void getCourseById_FoundTest() {
        Course course = new Course();
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(1);

        assertNotNull(result);
    }

    @Test
    void addCourseTest() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addCourse(course);

        assertEquals(course, result);
    }

    @Test
    void updateCourse_FoundTest() {
        Course existing = new Course();
        existing.setName("Java");

        Course updated = new Course();
        updated.setName("Python");

        when(courseRepository.findById(1)).thenReturn(Optional.of(existing));
        when(courseRepository.save(any())).thenReturn(existing);

        Course result = courseService.updateCourse(1, updated);

        assertEquals("Python", result.getName());
    }

    @Test
    void testDeleteCourse_NotFound_ShouldThrowException() {
        when(courseRepository.findById(100)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            courseService.deleteCourse(100);
        });

        assertEquals("Course not found with id: 100", ex.getMessage());
    }
}
