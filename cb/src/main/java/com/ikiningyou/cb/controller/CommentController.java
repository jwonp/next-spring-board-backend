package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.content.comment.CommentModifiedRequest;
import com.ikiningyou.cb.model.dto.content.comment.CommentRequest;
import com.ikiningyou.cb.model.dto.content.comment.CommentResponse;
import com.ikiningyou.cb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping
  public ResponseEntity<CommentResponse[]> getCommentByContentId(
    @RequestParam("id") Long id
  ) {
    CommentResponse[] commentList = commentService.getCommnetByContentId(id);
    if (commentList == null) {
      return ResponseEntity.status(400).body(null);
    }
    return ResponseEntity.ok().body(commentList);
  }

  @PostMapping
  public ResponseEntity<Boolean> addCommentByContentId(
    @RequestBody CommentRequest comment
  ) {
    boolean isSaved = commentService.addCommentByContentId(comment);
    return ResponseEntity.status(isSaved ? 200 : 400).body(isSaved);
  }

  @PatchMapping
  public ResponseEntity<Boolean> modifyComment(
    @RequestBody CommentModifiedRequest commentModifiedRequest
  ) {
    Long commentId = commentModifiedRequest.getCommentId();
    String comment = commentModifiedRequest.getComment();
    String writer = commentModifiedRequest.getWriter();
    boolean isModified = commentService.modifyComment(
      commentId,
      comment,
      writer
    );
    return ResponseEntity.ok().body(isModified);
  }

  @DeleteMapping
  public ResponseEntity<Boolean> deleteComment(
    @RequestParam("comment") Long commentId,
    @RequestParam("user") String userId
  ) {
    boolean isDeleted = commentService.deleteComment(commentId, userId);
    return ResponseEntity.ok().body(isDeleted);
  }

  @GetMapping("/amount")
  public ResponseEntity<Integer> getCommentAmountByContentId(
    @RequestParam("id") Long id
  ) {
    int amount = commentService.getCommentAmountByContentId(id);

    return ResponseEntity.ok().body(amount);
  }
}
