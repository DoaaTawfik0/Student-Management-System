package com.LearningSpringBoot.Student.Management.System;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class StudentManagementSystemApplication {

    @Value("${URL}")
    private String URL;
    @Value("${Pass}")
    private String Pass;

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @PostConstruct
    public void printEnvironmentVariable() {
        System.out.println("Value of URL: " + URL);
        System.out.println("Value of Pass: " + Pass);
        System.out.println(("Active environment: " + Arrays.toString(env.getActiveProfiles())));
    }


}
