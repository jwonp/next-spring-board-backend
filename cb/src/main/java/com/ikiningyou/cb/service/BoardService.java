package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.repository.ContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  @Autowired
  private ContentRepo contentRepo;

  public Boolean addContent(ContentRequest contentRequest) {
    Content content = Content
      .builder()
      .title(contentRequest.getTitle())
      .content(contentRequest.getContent())
      .writer(contentRequest.getWriter())
      .build();
    try {
      contentRepo.save(content);
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }
    return true;
  }
}
