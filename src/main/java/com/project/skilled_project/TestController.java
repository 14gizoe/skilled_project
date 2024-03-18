package com.project.skilled_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "14기조 cicd 완성이요~~!!!!!!!!!!!!!!";
  }
}
