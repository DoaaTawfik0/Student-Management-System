package controller;

import entity.Course;
import entity.Student;
import exception.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.CourseService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public List<Course> retrieveAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable int courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course == null)
            throw new NotFoundException("Course not found with id: " + courseId);
        return course;
    }

    @PostMapping("")
    public ResponseEntity<Student> addCourse(@Valid @RequestBody Course course) {
        Course savedCourse = courseService.addCourse(course);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{courseId}").buildAndExpand(savedCourse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Student> updateCourse(@PathVariable int courseId, @Valid @RequestBody Course updatedCourse) {
        Course savedStudent = courseService.updateCourse(courseId, updatedCourse);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(savedStudent.getId()).toUri();
        return ResponseEntity.ok().location(location).build();
    }


    /* make sure method done as one unit */
    @Transactional
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable int courseId) {

        Course course = courseService.getCourseById(courseId);

        if (course == null)
            return ResponseEntity.notFound().build();

        // Safe iteration
        List<Student> students = new ArrayList<>(course.getStudents());
        for (Student student : students) {
            student.getStudentCourses().remove(course);
        }

        course.getStudents().clear(); // Clear the owning side

        courseService.deleteCourse(courseId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/courses")
                .build()
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }


    @GetMapping("{courseId}/students")
    public Set<Student> retrieveAllStudentsInCourse(@PathVariable int courseId) {
        Course existingCourse = courseService.getCourseById(courseId);

        if (existingCourse == null)
            throw new NotFoundException("Course is not found with Id: " + courseId);

        return existingCourse.getStudents();
    }
}
