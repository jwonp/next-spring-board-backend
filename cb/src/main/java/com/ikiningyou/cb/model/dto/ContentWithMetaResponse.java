package com.ikiningyou.cb.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ContentWithMetaResponse {

  private Long contentId;
  private String title;
  private String content;
  private String board;
  private String author;
  private int views;
  private int likes;
  private Date updated;
}
