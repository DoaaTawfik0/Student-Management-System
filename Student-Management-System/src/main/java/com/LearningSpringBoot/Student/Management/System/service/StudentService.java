package com.LearningSpringBoot.Student.Management.System.service;


import com.LearningSpringBoot.Student.Management.System.entity.Course;
import com.LearningSpringBoot.Student.Management.System.entity.Student;
import com.LearningSpringBoot.Student.Management.System.exception.NotFoundException;
import com.LearningSpringBoot.Student.Management.System.repository.CourseRepository;
import com.LearningSpringBoot.Student.Management.System.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student updatedStudent) {
        Student existingStudent = checkStudentExist(id);

        /* do updates on fields*/
        existingStudent.setStudentName(updatedStudent.getStudentName());
        existingStudent.setStudentEmail(updatedStudent.getStudentEmail());
        existingStudent.setStudentAge(updatedStudent.getStudentAge());

        /* apply updates by saving new data */
        studentRepository.save(existingStudent);

        return existingStudent;
    }

    public void deleteStudent(int id) {
        checkStudentExist(id);

        studentRepository.deleteById(id);
    }

    public Student assignStudentToCourse(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + studentId));


        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with ID: " + courseId));

        // Remove associations from both sides
        student.getStudentCourses().remove(course);
        course.getStudents().remove(student);

        // Persist changes
        studentRepository.save(student);
        courseRepository.save(course);
    }

    private Student checkStudentExist(int id) {
        Student existingStudent = getStudentById(id);
        if (existingStudent == null)
            throw new NotFoundException("Student not found with id: " + id);

        return existingStudent;
    }

    public List<Student> getStudentsWithSortingUponSomeField(String field) {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<Student> getStudentsWithPagination(int pageNumber, int pageSize) {
        return studentRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Page<Student> getStudentsWithSortingAndPagination(String field, int pageNumber, int pageSize) {
        return studentRepository.findAll(PageRequest.of(pageNumber, pageSize).withSort( Sort.by(Sort.Direction.ASC, field)));
    }

}