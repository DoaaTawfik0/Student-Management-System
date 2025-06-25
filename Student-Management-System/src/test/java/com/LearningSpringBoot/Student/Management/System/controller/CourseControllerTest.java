package com.LearningSpringBoot.Student.Management.System.controller;

import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CourseService courseService;

    private Course createCourse(int id) {
        return new Course(id, "Math", "Math Course 101", null);
    }

    private Student createStudent() {
        Student student = new Student(1, "Ali", "ali@gmail.com", 22);
        student.setStudentCourses(new HashSet<>());

        return student;
    }

    @Test
    void retrieveAllCourses_ShouldReturnCourseList() throws Exception {
        when(courseService.getAllCourses()).thenReturn(List.of(createCourse(1), createCourse(2)));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getCourseById_ValidId_ShouldReturnCourse() throws Exception {
        Course course = createCourse(1);
        when(courseService.getCourseById(1)).thenReturn(course);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math"));
    }

    @Test
    void getCourseById_InvalidId_ShouldReturn404() throws Exception {
        when(courseService.getCourseById(999)).thenReturn(null);

        mockMvc.perform(get("/courses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCourse_ShouldReturnCreated() throws Exception {
        Course course = createCourse(1);
        when(courseService.addCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCourse_ShouldReturnOk() throws Exception {
        Course course = createCourse(1);
        when(courseService.updateCourse(eq(1), any(Course.class))).thenReturn(course);

        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourse_InvalidId_ShouldReturn404() throws Exception {
        when(courseService.getCourseById(999)).thenReturn(null);

        mockMvc.perform(delete("/courses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveAllStudentsInCourse_ShouldReturnStudentList() throws Exception {
        Student student = createStudent();
        Course course = createCourse(1);
        course.setStudents(Set.of(student));

        when(courseService.getCourseById(1)).thenReturn(course);

        mockMvc.perform(get("/courses/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].studentName").value("Ali"));
    }

    @Test
    void retrieveAllStudentsInCourse_CourseNotFound_ShouldReturn404() throws Exception {
        when(courseService.getCourseById(999)).thenReturn(null);

        mockMvc.perform(get("/courses/999/students"))
                .andExpect(status().isNotFound());
    }
}
