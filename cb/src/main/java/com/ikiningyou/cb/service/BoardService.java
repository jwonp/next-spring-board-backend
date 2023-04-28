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

  public Boolean addContent(ContentRequest contentRequest) {
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

  public ContentShortResponse getContentShortByContentId(Long contentId) {
    Optional<ContentShortResponse> rowContentShort = contentRepo.findByContentId(
      contentId
    );
    if (rowContentShort.isPresent() == false) {
      return null;
    }
    return rowContentShort.get();
  }

  public CommentResponse[] getCommnetByContentId(Long id) {
    Optional<List<CommentResponse>> rowCommentList = commentRepo.getCommentByContentId(
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
      .contentId(commentRequest.getContentId())
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

  public int getCommentAmountByContentId(Long id) {
    Long amount = commentRepo.countByContentId(id);
    int intSize = Long
      .valueOf(Optional.ofNullable(amount).orElse(0L))
      .intValue();
    return intSize;
  }

  @Transactional
  public boolean addLikeByContentIdAndUser(Long contentId, String userId) {
    Like like = Like.builder().contentId(contentId).userId(userId).build();
    Like[] rowExisted = getLikeByContentAndUser(contentId, userId);
    if (rowExisted.length > 0) {
      return false;
    }

    try {
      likeRepo.save(like);
      Optional<ContentMeta> rowContentMeta = contentMetaRepo.findByContentMetaId(
        contentId
      );
      if (rowContentMeta.isPresent()) {
        ContentMeta contentMeta = rowContentMeta.get();
        contentMeta.setLikes(contentMeta.getLikes() + 1);
      }
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }
    return true;
  }

  @Transactional
  public boolean deleteLikeByContentIdAndUser(Long contentId, String userId) {
    try {
      likeRepo.deleteByContentIdAndUserId(contentId, userId);
      Optional<ContentMeta> rowContentMeta = contentMetaRepo.findByContentMetaId(
        contentId
      );
      if (rowContentMeta.isPresent()) {
        ContentMeta contentMeta = rowContentMeta.get();
        contentMeta.setLikes(contentMeta.getLikes() - 1);
      }
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }

    return true;
  }

  public Like[] getLikeByContent(Long content) {
    Optional<List<Like>> rowLikeList = likeRepo.findByContentId(content);
    if (rowLikeList.isPresent() == false) {
      return null;
    }
    Like[] likeList = rowLikeList
      .get()
      .toArray(new Like[rowLikeList.get().size()]);
    return likeList;
  }

  public Like[] getLikeByUser(String user) {
    Optional<List<Like>> rowLikeList = likeRepo.findByUserId(user);
    if (rowLikeList.isPresent() == false) {
      return null;
    }
    Like[] likeList = rowLikeList
      .get()
      .toArray(new Like[rowLikeList.get().size()]);
    return likeList;
  }

  public Like[] getLikeByContentAndUser(Long content, String user) {
    Optional<List<Like>> rowLikeList = likeRepo.findByContentIdAndUserId(
      content,
      user
    );
    if (rowLikeList.isPresent() == false) {
      return null;
    }
    Like[] likeList = rowLikeList
      .get()
      .toArray(new Like[rowLikeList.get().size()]);
    return likeList;
  }

  public int getLikeCountByContentId(Long contentId) {
    Long rowCount = likeRepo.countByContentId(contentId);
    int intCount = Long
      .valueOf(Optional.ofNullable(rowCount).orElse(0L))
      .intValue();
    return intCount;
  }

  public boolean isLikedByContentIdAndUser(Long contentId, String user) {
    Long rowCount = likeRepo.countByContentIdAndUserId(contentId, user);
    int intCount = Long
      .valueOf(Optional.ofNullable(rowCount).orElse(0L))
      .intValue();
    if (intCount > 0) {
      return true;
    }
    return false;
  }

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
}
