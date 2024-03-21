package com.project.skilled_project.domain.user.controller;

import com.project.skilled_project.domain.user.dto.SignupRequestDto;
import com.project.skilled_project.domain.user.dto.UserDto;
import com.project.skilled_project.domain.user.service.UserService;
import com.project.skilled_project.global.response.CommonResponse;
import com.project.skilled_project.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @PutMapping
  public ResponseEntity<CommonResponse<Void>> updateUser(
      @RequestBody UserDto userDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    userService.updateUser(userDto.getEmail(), userDto.getUsername(), userDto.getPassword(),
        userDetails.getUser().getId());
    return CommonResponse.ok(null);
  }

  @DeleteMapping
  public ResponseEntity<CommonResponse<Void>> deleteUser(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    userService.deleteUser(userDetails.getUser().getId());
    return CommonResponse.ok(null);
  }
}
