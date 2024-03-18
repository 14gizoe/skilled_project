package com.project.skilled_project.domain.user.service;

import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

}
