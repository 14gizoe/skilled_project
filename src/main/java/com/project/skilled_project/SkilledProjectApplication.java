package com.project.skilled_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SkilledProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkilledProjectApplication.class, args);
  }

}
