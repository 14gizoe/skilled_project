package com.project.skilled_project.domain.user.repository;

import com.project.skilled_project.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT COUNT(u.id) > 0 " +
      "FROM User u " +
      "WHERE u.email =:email OR u.username =:username")
  boolean existsByEmailOrUsername(@Param("email") String email, @Param("username") String username);

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);
}
