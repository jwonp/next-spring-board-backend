package com.ikiningyou.cb.model.dto;

public interface CommentResponse {
  public Long getCommentId();

  public Long getContentId();

  public String getComment();

  public String getWriter();

  public String getCreated();

  public String getUpdated();
}
