package com.project.skilled_project.domain.user.entity;

import com.project.skilled_project.global.util.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@SQLRestriction(value = "deleted_at is NULL")
public class User extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column
  private String profileImage;

  public User(String email, String username, String password, String profileImage) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.profileImage = profileImage;
  }

  public User(String username) {
    this.username = username;
  }

  public void update(String email, String username, String password, String profileImage) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.profileImage = profileImage;
  }
}
