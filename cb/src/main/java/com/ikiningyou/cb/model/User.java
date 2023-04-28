package com.ikiningyou.cb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

  @Id
  private String id;

  @Column(name = "name", nullable = true)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "image", nullable = true)
  private String image;

  @Column(name = "provider", nullable = false)
  private String provider;
}
