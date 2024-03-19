package com.project.skilled_project.domain.user.controller;

import com.project.skilled_project.domain.user.dto.SignupRequestDto;
import com.project.skilled_project.domain.user.service.UserService;
import com.project.skilled_project.domain.user.service.UserServiceImpl;
import com.project.skilled_project.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<CommonResponse<Void>> signup(@RequestBody SignupRequestDto signupRequest) {
    userService.signup(signupRequest.getEmail(), signupRequest.getUsername(),
        signupRequest.getPassword());
    return CommonResponse.ok(null);
  }
}
