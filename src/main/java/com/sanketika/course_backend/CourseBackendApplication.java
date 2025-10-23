package com.sanketika.course_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CourseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseBackendApplication.class, args);
	}

}
