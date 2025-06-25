package com.LearningSpringBoot.Student.Management.System;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentManagementSystemApplication {

	@Value("${URL}")
	private String URL;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}

	@PostConstruct
	public void printEnvironmentVariable() {
		System.out.println("Value of URL: " + URL);
	}


}
