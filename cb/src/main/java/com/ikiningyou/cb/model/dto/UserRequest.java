package com.ikiningyou.cb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequest {

  private String id;
  private String name;
  private String email;
  private String image;
  private String provider;
}
