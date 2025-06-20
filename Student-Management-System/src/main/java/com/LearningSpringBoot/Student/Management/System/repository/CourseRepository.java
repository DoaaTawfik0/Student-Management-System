package com.LearningSpringBoot.Student.Management.System.repository;

import com.LearningSpringBoot.Student.Management.System.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
