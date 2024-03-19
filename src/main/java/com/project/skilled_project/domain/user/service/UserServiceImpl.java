package com.project.skilled_project.domain.user.service;

import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.repository.RefreshTokenRepository;
import com.project.skilled_project.domain.user.repository.UserRepository;
import com.project.skilled_project.domain.user.repository.UserRepositoryQuery;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${defaultImage.path}")
  private String localPath;

  @Override
  @Transactional
  public void signup(String email, String username, String password) {
    if (userRepository.existsByEmailOrUsername(email, username)) {
      throw new IllegalArgumentException("존재하는 계정입니다.");
    }

    String encodedPassword = passwordEncoder.encode(password);

    User user = new User(email, username, encodedPassword, localPath);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void updateUser(String email, String username, String password, String verifiedUser) {
    User user = userRepository.findByUsername(verifiedUser).orElseThrow(() ->
      new UsernameNotFoundException("존재하지 않는 유저입니다.")
    );

    if (userRepository.existsByEmailOrUsername(email, username)) {
      throw new IllegalArgumentException("존재하는 계정입니다.");
    }

    String encodedPassword = passwordEncoder.encode(password);
    user.update(email, username, encodedPassword, localPath);
    refreshTokenRepository.delete(username);
  }

  @Override
  @Transactional
  public void deleteUser(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("존재하지 않는 유저입니다.")
    );

    user.delete(UUID.randomUUID().toString());
  }
}
