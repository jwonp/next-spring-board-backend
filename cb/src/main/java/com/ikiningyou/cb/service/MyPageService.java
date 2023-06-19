package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.repository.MyPageRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

  @Autowired
  private MyPageRepo myPageRepo;

  public ContentMetaResponse[] getLikedContents(String userId, String index) {
    int pageableIndex;
    if (index == null) {
      pageableIndex = 0;
    } else {
      pageableIndex = Integer.parseInt(index);
    }
    Optional<List<ContentMetaResponse>> rowContents = myPageRepo.getLikedContents(
      userId,
      PageRequest.of(pageableIndex, 10)
    );
    if (rowContents.isPresent() == false) {
      return null;
    }

    return rowContents
      .get()
      .toArray(new ContentMetaResponse[rowContents.get().size()]);
  }

  public ContentMetaResponse[] getWrittenContents(String userId, String index) {
    int pageableIndex;
    if (index == null) {
      pageableIndex = 0;
    } else {
      pageableIndex = Integer.parseInt(index);
    }
    Optional<List<ContentMetaResponse>> rowContents = myPageRepo.getWrittenContents(
      userId,
      PageRequest.of(pageableIndex, 10)
    );
    if (rowContents.isPresent() == false) {
      return null;
    }

    return rowContents
      .get()
      .toArray(new ContentMetaResponse[rowContents.get().size()]);
  }
}
