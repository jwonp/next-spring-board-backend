package com.ikiningyou.cb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ContentRequest {

  private String title;
  private String content;
  private String writer;
  private String board;
}
