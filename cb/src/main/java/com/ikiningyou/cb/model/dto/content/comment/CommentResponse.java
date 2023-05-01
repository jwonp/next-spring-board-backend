package com.ikiningyou.cb.model.dto.content.comment;

public interface CommentResponse {
  public Long getCommentId();

  public Long getContentId();

  public String getComment();

  public String getWriterId();

  public String getWriter();

  public String getCreated();

  public String getUpdated();
}
