package com.LearningSpringBoot.Student.Management.System.repository;

import com.LearningSpringBoot.Student.Management.System.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
