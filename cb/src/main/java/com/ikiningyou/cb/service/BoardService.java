package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Comment;
import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.Like;
import com.ikiningyou.cb.model.dto.content.ContentFullData;
import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.model.dto.content.ContentRequest;
import com.ikiningyou.cb.model.dto.content.ContentShortResponse;
import com.ikiningyou.cb.model.dto.content.comment.CommentRequest;
import com.ikiningyou.cb.model.dto.content.comment.CommentResponse;
import com.ikiningyou.cb.repository.CommentRepo;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.ContentRepo;
import com.ikiningyou.cb.repository.LikeRepo;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BoardService {

  @Autowired
  private ContentRepo contentRepo;

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private LikeRepo likeRepo;

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
}
