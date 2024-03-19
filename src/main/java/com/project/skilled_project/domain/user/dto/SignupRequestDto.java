package com.project.skilled_project.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

  private String email;

  private String password;

  private String username;
}
