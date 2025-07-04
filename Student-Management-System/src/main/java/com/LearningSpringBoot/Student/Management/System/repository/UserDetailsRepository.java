package com.LearningSpringBoot.Student.Management.System.repository;

import com.LearningSpringBoot.Student.Management.System.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String userName);
}
