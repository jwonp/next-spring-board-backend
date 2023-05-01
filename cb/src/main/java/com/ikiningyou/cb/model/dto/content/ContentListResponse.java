package com.ikiningyou.cb.model.dto.content;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContentListResponse {

  private Long contentId;
  private String title;
  private int views;
  private int likes;
  private String author;
  private String board;
  private Date updated;
}
