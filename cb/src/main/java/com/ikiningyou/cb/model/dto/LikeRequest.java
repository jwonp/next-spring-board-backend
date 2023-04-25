package com.ikiningyou.cb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikeRequest {

  private Long contentId;
  private String user;
}
