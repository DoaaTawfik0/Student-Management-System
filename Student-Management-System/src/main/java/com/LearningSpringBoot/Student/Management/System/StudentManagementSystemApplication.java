package com.LearningSpringBoot.Student.Management.System;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
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
        log.info("Value of URL: {}", URL);
        log.info("Value of Pass: {}", Pass);
        log.info("Active environment: {}", Arrays.toString(env.getActiveProfiles()));
    }


}
