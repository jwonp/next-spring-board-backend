package com.ikiningyou.cb.model.dto.content;

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
  private String author;
  private String board;
}
