package com.project.skilled_project.domain.user.service;

import com.project.skilled_project.domain.user.entity.User;

public interface UserService {

  void signup(String email, String username, String password);

  void updateUser(String email, String username, String password, Long userId);

  void deleteUser(Long userId);

  User findUser(Long userId);
}
