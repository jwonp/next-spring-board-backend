package com.ikiningyou.cb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContentRequest {

  private String title;
  private String content;
  private String writer;
}
