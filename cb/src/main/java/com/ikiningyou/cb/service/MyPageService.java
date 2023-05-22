package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

  private ContentMetaRepo contentMetaRepo;

  public ContentMetaResponse[] getLikedContents(String userId) {
    return null;
  }

  public ContentMetaResponse[] getWrittenContents(String userId) {
    return null;
  }
}
