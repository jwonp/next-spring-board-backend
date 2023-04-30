package com.ikiningyou.cb.model.dto.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ContentModifiedRequest {

  Long contentId;
  String title;
  String contents;
  String author;
}
