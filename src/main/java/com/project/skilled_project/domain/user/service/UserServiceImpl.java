package com.project.skilled_project.domain.user.service;

import com.project.skilled_project.domain.file.service.FileService;
import com.project.skilled_project.domain.user.entity.User;
import com.project.skilled_project.domain.user.repository.RefreshTokenRepository;
import com.project.skilled_project.domain.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

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
  public void updateUser(String email, String username, String password, Long userId) {
    User user = findUser(userId);

    if (userRepository.existsByEmailOrUsername(email, username)) {
      throw new IllegalArgumentException("존재하는 계정입니다.");
    }

    String encodedPassword = passwordEncoder.encode(password);
    String filePath = fileService.getFilePath(userId, "profile");
    user.update(email, username, encodedPassword, filePath);
    refreshTokenRepository.delete(username);
  }

  @Override
  @Transactional
  public void deleteUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() ->
        new UsernameNotFoundException("존재하지 않는 유저입니다.")
    );

    user.delete(UUID.randomUUID().toString());
  }

  @Override
  public User findUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() ->
        new UsernameNotFoundException("존재하지 않는 유저입니다.")
    );
    return user;
  }
}
