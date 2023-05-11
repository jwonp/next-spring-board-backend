package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.Like;
import com.ikiningyou.cb.model.dto.content.like.LikeRequest;
import com.ikiningyou.cb.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("like")
public class LikeController {

  @Autowired
  private LikeService likeService;

  @PostMapping
  public ResponseEntity<Boolean> addLikeByContentIdAndUser(
    @RequestBody LikeRequest likeRequest
  ) {
    boolean like = likeService.addLikeByContentIdAndUser(
      likeRequest.getContentId(),
      likeRequest.getUser()
    );
    return ResponseEntity.ok().body(like);
  }

  @DeleteMapping
  public ResponseEntity<Boolean> deleteLikeByContentId(
    @RequestParam("content") Long contentId,
    @RequestParam("user") String userId
  ) {
    boolean isDeleted = likeService.deleteLikeByContentIdAndUser(
      contentId,
      userId
    );
    return ResponseEntity.ok().body(isDeleted);
  }

  @GetMapping
  public ResponseEntity<Like[]> getLikeByContentOrUser(
    @RequestParam(required = false, value = "content") Long contentId,
    @RequestParam(required = false, value = "user") String userId
  ) {
    Like[] likeLlist = null;

    if (contentId == null && userId == null) return ResponseEntity
      .status(201)
      .body(null);

    //if exist contentId, userId
    if (contentId != null && userId != null) {
      likeLlist = likeService.getLikeByContentAndUser(contentId, userId);
    }

    //if exist only userId
    if (contentId == null) {
      likeLlist = likeService.getLikeByUser(userId);
    }

    //if exist only contentId
    if (userId == null) {
      likeLlist = likeService.getLikeByContent(contentId);
    }

    return ResponseEntity.ok().body(likeLlist);
  }

  @GetMapping("/amount")
  public ResponseEntity<Integer> getLikeCountByContentId(
    @RequestParam("content") Long contentId
  ) {
    int likeCount = likeService.getLikeCountByContentId(contentId);
    return ResponseEntity.ok().body(likeCount);
  }

  @GetMapping("/liked")
  public ResponseEntity<Boolean> isLikedByContentIdAndUser(
    @RequestParam("content") Long contentId,
    @RequestParam("user") String userId
  ) {
    Boolean isLiked = likeService.isLikedByContentIdAndUser(contentId, userId);
    return ResponseEntity.status(200).body(isLiked);
  }
}
