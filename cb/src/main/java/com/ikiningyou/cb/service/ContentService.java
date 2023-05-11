package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.content.ContentFullData;
import com.ikiningyou.cb.model.dto.content.ContentRequest;
import com.ikiningyou.cb.model.dto.content.ContentShortResponse;
import com.ikiningyou.cb.repository.CommentRepo;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.ContentRepo;
import com.ikiningyou.cb.repository.LikeRepo;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentService {

  @Autowired
  private ContentRepo contentRepo;

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private LikeRepo likeRepo;

  public boolean isAuthorByContentId(Long contentId, String author) {
    Long rowContentCount = contentRepo.countByContentIdAndAuthor(
      contentId,
      author
    );
    int intCount = Long
      .valueOf(Optional.ofNullable(rowContentCount).orElse(0L))
      .intValue();
    if (intCount > 0) {
      return true;
    }
    return false;
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

  @Transactional
  public boolean deleteContent(Long contentId, String userId) {
    if (isAuthorByContentId(contentId, userId) == false) {
      return false;
    }
    try {
      contentRepo.deleteByContentId(contentId);
      contentMetaRepo.deleteByContentMetaId(contentId);
      commentRepo.deleteByContentId(contentId);
      likeRepo.deleteByContentId(contentId);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public ContentShortResponse getContentShortByContentId(Long contentId) {
    Optional<ContentShortResponse> rowContentShort = contentRepo.findByContentId(
      contentId
    );
    if (rowContentShort.isPresent() == false) {
      return null;
    }
    return rowContentShort.get();
  }

  @Transactional
  public Long modifyContent(
    Long contentId,
    String title,
    String contents,
    String author
  ) {
    if (isAuthorByContentId(contentId, author) == false) {
      return -1l;
    }
    Long modifiedContentId = -1l;
    Optional<Content> rowContent = contentRepo.findById(contentId);
    Optional<ContentMeta> rowContentMeta = contentMetaRepo.findById(contentId);
    if (rowContent.isPresent()) {
      Content content = rowContent.get();
      modifiedContentId = content.getContentId();
      content.setContent(contents);
      content.setTitle(title);
    }
    if (rowContentMeta.isPresent()) {
      ContentMeta contentMeta = rowContentMeta.get();
      contentMeta.setTitle(title);
      contentMeta.setUpdated(new Date());
    }
    return modifiedContentId;
  }

  public Long addContent(ContentRequest contentRequest) {
    Content savedContent;
    Content content = Content
      .builder()
      .title(contentRequest.getTitle())
      .content(contentRequest.getContent())
      .author(contentRequest.getAuthor())
      .board(contentRequest.getBoard())
      .build();
    ContentMeta contentMeta = ContentMeta
      .builder()
      .board(contentRequest.getBoard())
      .title(contentRequest.getTitle())
      .author(contentRequest.getAuthor())
      .build();
    try {
      savedContent = contentRepo.save(content);
      contentMetaRepo.save(contentMeta);
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return -1l;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return -1l;
    }
    return savedContent.getContentId();
  }
}
