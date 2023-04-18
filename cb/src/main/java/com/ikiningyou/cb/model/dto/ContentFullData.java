package com.ikiningyou.cb.model.dto;

import java.util.Date;

public interface ContentFullData {
  public Long getId();

  public String getTitle();

  public String getContent();

  public String getBoard();

  public String getAuthor();

  public int getViews();

  public int getLikes();

  public Date getUpdated();
}
