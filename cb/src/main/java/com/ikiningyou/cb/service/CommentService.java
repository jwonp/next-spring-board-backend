package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Comment;
import com.ikiningyou.cb.model.dto.content.comment.CommentRequest;
import com.ikiningyou.cb.model.dto.content.comment.CommentResponse;
import com.ikiningyou.cb.repository.CommentRepo;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  @Autowired
  private CommentRepo commentRepo;

  public boolean isWriterByCommentId(Long commentId, String writer) {
    Long rowCommentCount = commentRepo.countByCommentIdAndWriter(
      commentId,
      writer
    );
    int intCount = Long
      .valueOf(Optional.ofNullable(rowCommentCount).orElse(0L))
      .intValue();
    if (intCount > 0) {
      return true;
    }
    return false;
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
  public boolean modifyComment(Long commentId, String comments, String writer) {
    if (isWriterByCommentId(commentId, writer) == false) {
      return false;
    }
    Optional<Comment> rowComment = commentRepo.findById(commentId);
    if (rowComment.isPresent() == false) {
      return false;
    }
    Comment comment = rowComment.get();
    comment.setComment(comments);
    comment.setUpdated(new Date());

    return true;
  }

  @Transactional
  public boolean deleteComment(Long commentId, String userId) {
    if (isWriterByCommentId(commentId, userId) == false) {
      return false;
    }
    try {
      commentRepo.deleteByCommentId(commentId);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
