package com.ikiningyou.cb.model;

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

  private String name;
  private String email;
  private String image;
  private String provider;
}
