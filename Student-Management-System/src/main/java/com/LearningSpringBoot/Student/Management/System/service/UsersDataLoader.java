package com.LearningSpringBoot.Student.Management.System.service;

import com.LearningSpringBoot.Student.Management.System.entity.Users;
import com.LearningSpringBoot.Student.Management.System.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersDataLoader {
    @Bean
    public CommandLineRunner runner(UserDetailsRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty() && repo.findByUsername("user").isEmpty()) {
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ADMIN");

                Users user = new Users();
                user.setUsername("user");
                user.setPassword(encoder.encode("user123"));
                user.setRole("USER");

                repo.save(admin);
                repo.save(user);
            }
        };
    }


}
