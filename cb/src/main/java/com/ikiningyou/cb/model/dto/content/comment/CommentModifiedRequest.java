package com.ikiningyou.cb.model.dto.content.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentModifiedRequest {

  private Long commentId;
  private String comment;
  private String writer;
}
