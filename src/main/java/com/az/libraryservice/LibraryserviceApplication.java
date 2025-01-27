package com.az.libraryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LibraryserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryserviceApplication.class, args);
	}
}
