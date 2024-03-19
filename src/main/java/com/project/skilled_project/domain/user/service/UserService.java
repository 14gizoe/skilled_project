package com.project.skilled_project.domain.user.service;

import com.project.skilled_project.domain.user.entity.User;

public interface UserService {

  void signup(String email, String username, String password);

  void updateUser(String email, String username, String password, String verifiedUser);

  void deleteUser(String username);

  User findUser(String username);
}
