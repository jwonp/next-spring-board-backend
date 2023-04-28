package com.ikiningyou.cb.model.dto.content.like;

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
