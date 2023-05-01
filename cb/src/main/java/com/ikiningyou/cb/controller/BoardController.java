package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.Like;
import com.ikiningyou.cb.model.dto.content.ContentFullData;
import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.model.dto.content.ContentModifiedRequest;
import com.ikiningyou.cb.model.dto.content.ContentRequest;
import com.ikiningyou.cb.model.dto.content.ContentShortResponse;
import com.ikiningyou.cb.model.dto.content.comment.CommentModifiedRequest;
import com.ikiningyou.cb.model.dto.content.comment.CommentRequest;
import com.ikiningyou.cb.model.dto.content.comment.CommentResponse;
import com.ikiningyou.cb.model.dto.content.like.LikeRequest;
import com.ikiningyou.cb.service.BoardService;
import com.ikiningyou.cb.util.BoardNameMap;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

  @Autowired
  private BoardService boardService;

  private BoardNameMap boardNameMap;

  private BoardController() {
    this.boardNameMap = new BoardNameMap();
  }

  @GetMapping("/list")
  public ResponseEntity<ContentMetaResponse[]> getContentListByBoard(
    @RequestParam("board") String board,
    @RequestParam("index") int index
  ) {
    ContentMetaResponse[] contentMetaList = boardService.getContentListByBoard(
      board,
      index
    );

    if (contentMetaList == null) {
      return ResponseEntity.status(201).body(null);
    }

    return ResponseEntity.ok().body(contentMetaList);
  }

  @GetMapping("/size")
  public ResponseEntity<Integer> getSizeByBoard(
    @RequestParam("board") String board,
    @RequestParam(required = false, value = "search") String search
  ) {
    if (boardNameMap.isBoardName(board) == false) {
      return ResponseEntity.status(201).body(-1);
    }
    Long size = boardService.getSizeByBoard(board, search);
    int intSize = Long.valueOf(Optional.ofNullable(size).orElse(0L)).intValue();

    return ResponseEntity.status(200).body(intSize);
  }

  @GetMapping("/search")
  public ResponseEntity<ContentMetaResponse[]> searchByQueryAndBoard(
    @RequestParam("search") String search,
    @RequestParam("board") String board
  ) {
    ContentMetaResponse[] resultList = boardService.searchByQueryAndBoard(
      board,
      search
    );
    if (resultList == null) {
      return ResponseEntity.status(201).body(null);
    }
    return ResponseEntity.status(200).body(resultList);
  }

  @PostMapping("/edit")
  public ResponseEntity<Boolean> saveContent(
    @RequestBody ContentRequest content
  ) {
    Boolean isSaved = false;
    Boolean isBoardNameCorrect = false;
    Boolean isSuccessd = false;

    if (boardNameMap.isBoardName(content.getBoard())) {
      isBoardNameCorrect = true;
      isSaved = boardService.addContent(content);
    }
    isSuccessd = isSaved && isBoardNameCorrect;

    return ResponseEntity.status(isSuccessd ? 200 : 400).body(isSuccessd);
  }

  @PatchMapping("/modify")
  public ResponseEntity<Boolean> modifyContent(
    @RequestBody ContentModifiedRequest contentModifiedRequest
  ) {
    Long contentId = contentModifiedRequest.getContentId();
    String title = contentModifiedRequest.getTitle();
    String contents = contentModifiedRequest.getContents();
    String author = contentModifiedRequest.getAuthor();

    boolean isModified = boardService.modifyContent(
      contentId,
      title,
      contents,
      author
    );
    return ResponseEntity.ok().body(isModified);
  }

  @GetMapping("/content")
  public ResponseEntity<ContentFullData> getContentByContentId(
    @RequestParam("id") Long id
  ) {
    ContentFullData content = boardService.getContentById(id);
    if (content == null) {
      return ResponseEntity.status(201).body(null);
    }
    return ResponseEntity.ok().body(content);
  }

  @GetMapping("/content/short")
  public ResponseEntity<ContentShortResponse> getContentShortByContentId(
    @RequestParam("id") Long contentId
  ) {
    ContentShortResponse contentShort = boardService.getContentShortByContentId(
      contentId
    );
    if (contentShort == null) return ResponseEntity.status(201).body(null);
    return ResponseEntity.status(200).body(contentShort);
  }

  @DeleteMapping("/content")
  public ResponseEntity<Boolean> deleteContent(
    @RequestParam("content") Long contentId,
    @RequestParam("user") String userId
  ) {
    boardService.deleteContent(contentId, userId);
    return ResponseEntity.status(200).body(null);
  }

  @GetMapping("/comment")
  public ResponseEntity<CommentResponse[]> getCommentByContentId(
    @RequestParam("id") Long id
  ) {
    CommentResponse[] commentList = boardService.getCommnetByContentId(id);
    if (commentList == null) {
      return ResponseEntity.status(201).body(null);
    }
    return ResponseEntity.ok().body(commentList);
  }

  @PostMapping("/comment")
  public ResponseEntity<Boolean> addCommentByContentId(
    @RequestBody CommentRequest comment
  ) {
    boolean isSaved = boardService.addCommentByContentId(comment);
    return ResponseEntity.status(isSaved ? 200 : 201).body(isSaved);
  }

  @PatchMapping("/comment")
  public ResponseEntity<Boolean> modifyComment(
    @RequestBody CommentModifiedRequest commentModifiedRequest
  ) {
    Long commentId = commentModifiedRequest.getCommentId();
    String comment = commentModifiedRequest.getComment();
    String writer = commentModifiedRequest.getWriter();
    boolean isModified = boardService.modifyComment(commentId, comment, writer);
    return ResponseEntity.ok().body(isModified);
  }

  @DeleteMapping("/comment")
  public ResponseEntity<Boolean> deleteComment(
    @RequestParam("comment") Long commentId,
    @RequestParam("user") String userId
  ) {
    boolean isDeleted = boardService.deleteComment(commentId, userId);
    return ResponseEntity.ok().body(isDeleted);
  }

  @GetMapping("/comment/amount")
  public ResponseEntity<Integer> getCommentAmountByContentId(
    @RequestParam("id") Long id
  ) {
    int amount = boardService.getCommentAmountByContentId(id);

    return ResponseEntity.ok().body(amount);
  }

  @PostMapping("/content/like")
  public ResponseEntity<Boolean> addLikeByContentIdAndUser(
    @RequestBody LikeRequest likeRequest
  ) {
    boolean like = boardService.addLikeByContentIdAndUser(
      likeRequest.getContentId(),
      likeRequest.getUser()
    );
    return ResponseEntity.ok().body(like);
  }

  @DeleteMapping("/content/like")
  public ResponseEntity<Boolean> deleteLikeByContentId(
    @RequestParam("content") Long contentId,
    @RequestParam("user") String userId
  ) {
    boolean isDeleted = boardService.deleteLikeByContentIdAndUser(
      contentId,
      userId
    );
    return ResponseEntity.ok().body(isDeleted);
  }

  @GetMapping("/content/like")
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
      likeLlist = boardService.getLikeByContentAndUser(contentId, userId);
    }

    //if exist only userId
    if (contentId == null) {
      likeLlist = boardService.getLikeByUser(userId);
    }

    //if exist only contentId
    if (userId == null) {
      likeLlist = boardService.getLikeByContent(contentId);
    }

    return ResponseEntity.ok().body(likeLlist);
  }

  @GetMapping("/content/like/amount")
  public ResponseEntity<Integer> getLikeCountByContentId(
    @RequestParam("content") Long contentId
  ) {
    int likeCount = boardService.getLikeCountByContentId(contentId);
    return ResponseEntity.ok().body(likeCount);
  }

  @GetMapping("/content/liked")
  public ResponseEntity<Boolean> isLikedByContentIdAndUser(
    @RequestParam("content") Long contentId,
    @RequestParam("user") String userId
  ) {
    Boolean isLiked = boardService.isLikedByContentIdAndUser(contentId, userId);
    return ResponseEntity.status(200).body(isLiked);
  }

  @GetMapping("/content/author")
  public ResponseEntity<Boolean> isAuthorByContentId(
    @RequestParam("content") Long contentId,
    @RequestParam("author") String author
  ) {
    boolean isAuthor = boardService.isAuthorByContentId(contentId, author);
    return ResponseEntity.status(200).body(isAuthor);
  }
}
