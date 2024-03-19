package com.project.skilled_project.domain.user.repository;

import com.project.skilled_project.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmailOrUsername(String email, String username);

  Optional<User> findByEmail(String email);
}
