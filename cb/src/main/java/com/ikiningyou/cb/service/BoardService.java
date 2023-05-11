package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardService {

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  public ContentMetaResponse[] getContentListByBoard(String board, int index) {
    Pageable pageable = PageRequest.of(index, 10);
    Optional<List<ContentMetaResponse>> contentMetaList = contentMetaRepo.findByBoard(
      board,
      pageable
    );
    if (contentMetaList.isPresent()) {
      return contentMetaList
        .get()
        .toArray(new ContentMetaResponse[contentMetaList.get().size()]);
    }

    return null;
  }

  public Long getSizeByBoard(String board, String search) {
    Long size = 0l;
    if (search == null) {
      size = contentMetaRepo.countByBoard(board);
    } else {
      size = contentMetaRepo.countByBoardAndTitleContaining(board, search);
    }

    return size;
  }

  public ContentMetaResponse[] searchByQueryAndBoard(
    String board,
    String search,
    int index
  ) {
    Pageable pageable = PageRequest.of(index, 10);
    Optional<List<ContentMetaResponse>> searchResult = contentMetaRepo.findByBoardAndTitleContaining(
      board,
      search,
      pageable
    );

    if (searchResult.isPresent()) {
      return searchResult
        .get()
        .toArray(new ContentMetaResponse[searchResult.get().size()]);
    }
    return null;
  }

  public ContentMetaResponse[] getMostLikedContentMeta() {
    Optional<List<ContentMetaResponse>> rowContentMetaList = contentMetaRepo.getMostLikedContentMeta();
    if (rowContentMetaList.isPresent() == false) {
      return null;
    }
    List<ContentMetaResponse> contentMetaList = rowContentMetaList.get();

    return contentMetaList.toArray(
      new ContentMetaResponse[contentMetaList.size()]
    );
  }

  public ContentMetaResponse[] getMostViewedContentMeta() {
    Optional<List<ContentMetaResponse>> rowContentMetaList = contentMetaRepo.getMostViewedContentMeta();
    if (rowContentMetaList.isPresent() == false) {
      return null;
    }
    List<ContentMetaResponse> contentMetaList = rowContentMetaList.get();

    return contentMetaList.toArray(
      new ContentMetaResponse[contentMetaList.size()]
    );
  }

  public ContentMetaResponse[] getRecentlyContentMeta() {
    Optional<List<ContentMetaResponse>> rowContentMetaList = contentMetaRepo.getRecentlyContentMeta();
    if (rowContentMetaList.isPresent() == false) {
      return null;
    }
    List<ContentMetaResponse> contentMetaList = rowContentMetaList.get();

    return contentMetaList.toArray(
      new ContentMetaResponse[contentMetaList.size()]
    );
  }
}
