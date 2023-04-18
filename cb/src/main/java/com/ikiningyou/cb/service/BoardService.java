package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.ContentFullData;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.ContentRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  @Autowired
  private ContentRepo contentRepo;

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  public Optional<List<ContentMeta>> getContentListByBoard(
    String board,
    int index
  ) {
    Pageable pageable = PageRequest.of(index, 10);
    Optional<List<ContentMeta>> contentMetaList = contentMetaRepo.findByBoard(
      board,
      pageable
    );
    return contentMetaList;
  }

  public Boolean addContent(ContentRequest contentRequest) {
    Content content = Content
      .builder()
      .title(contentRequest.getTitle())
      .content(contentRequest.getContent())
      .writer(contentRequest.getWriter())
      .board(contentRequest.getBoard())
      .build();
    ContentMeta contentMeta = ContentMeta
      .builder()
      .board(contentRequest.getBoard())
      .title(contentRequest.getTitle())
      .author(contentRequest.getWriter())
      .build();
    try {
      contentRepo.save(content);
      contentMetaRepo.save(contentMeta);
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }
    return true;
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

  public List<ContentMeta> searchByQueryAndBoard(String board, String search) {
    Pageable pageable = PageRequest.of(0, 10);
    Optional<List<ContentMeta>> searchResult = contentMetaRepo.findByBoardAndTitleContaining(
      board,
      search,
      pageable
    );

    if (searchResult.isPresent()) {
      return searchResult.get();
    }
    return null;
  }

  public ContentFullData getContentByIdAndBoard(String board, Long id) {
    Optional<ContentFullData> content = contentRepo.getContentWithContentMeta(
      id,
      board
    );

    if (content.isPresent() == false) {
      return null;
    }

    return content.get();
  }
}
