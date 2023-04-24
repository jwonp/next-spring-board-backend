package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Comment;
import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.CommentRequest;
import com.ikiningyou.cb.model.dto.CommentResponse;
import com.ikiningyou.cb.model.dto.ContentFullData;
import com.ikiningyou.cb.model.dto.ContentMetaResponse;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.repository.CommentRepo;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.ContentRepo;
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

  public ContentMetaResponse[] searchByQueryAndBoard(
    String board,
    String search
  ) {
    Pageable pageable = PageRequest.of(0, 10);
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

  @Transactional
  public ContentFullData getContentById(Long id) {
    Optional<ContentMeta> meta = contentMetaRepo.findById(id);
    if (meta.isPresent()) {
      ContentMeta _meta = meta.get();

      _meta.setViews(_meta.getViews() + 1);
    }

    Optional<ContentFullData> content = contentRepo.getContentWithContentMeta(
      id
    );

    if (content.isPresent() == false) {
      return null;
    }

    return content.get();
  }

  public CommentResponse[] getCommnetByContentId(Long id) {
    Optional<List<CommentResponse>> rowCommentList = commentRepo.getCommentByContent(
      id
    );
    if (rowCommentList.isPresent() == false) {
      return null;
    }
    return rowCommentList
      .get()
      .toArray(new CommentResponse[rowCommentList.get().size()]);
  }

  public boolean addCommentByContentId(CommentRequest commentRequest) {
    Comment comment = Comment
      .builder()
      .content(commentRequest.getContentId())
      .comment(commentRequest.getComment())
      .writer(commentRequest.getWriter())
      .build();

    try {
      commentRepo.save(comment);
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }
    return true;
  }

  public Long getCommentAmountByContentId(Long id) {
    Long amount = commentRepo.countByContent(id);
    return amount;
  }
}
