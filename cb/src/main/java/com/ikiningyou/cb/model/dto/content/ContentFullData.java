package com.ikiningyou.cb.model.dto.content;

import java.util.Date;

public interface ContentFullData {
  public Long getContentId();

  public String getTitle();

  public String getContent();

  public String getBoard();

  public String getAuthorId();

  public String getAuthor();

  public int getViews();

  public int getLikes();

  public String getCreated();
}
