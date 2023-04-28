package com.ikiningyou.cb.model.dto.content;

public interface ContentMetaResponse {
  public Long getContentMetaId();

  public String getTitle();

  public String getAuthor();

  public String getBoard();

  public String getCreated();

  public String getUpdated();

  public int getViews();

  public int getLikes();
}
