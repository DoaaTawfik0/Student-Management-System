package com.LearningSpringBoot.Student.Management.System.service;

import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import com.LearningSpringBoot.Student.Management.System.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course addCourse(@Valid Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(int id, @Valid Course updatedCourse) {
        Course existingCourse = checkCourseExist(id);

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());

        courseRepository.save(existingCourse);

        return existingCourse;
    }

    public void deleteCourse(int id) {
        checkCourseExist(id);

        courseRepository.deleteById(id);
    }

    private Course checkCourseExist(int id) {
        Course existingCourse = getCourseById(id);
        if (existingCourse == null)
            throw new NotFoundException("Course not found with id: " + id);

        return existingCourse;
    }

}
