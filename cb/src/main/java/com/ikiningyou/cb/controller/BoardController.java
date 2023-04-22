package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.Comment;
import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.CommentRequest;
import com.ikiningyou.cb.model.dto.CommentResponse;
import com.ikiningyou.cb.model.dto.ContentFullData;
import com.ikiningyou.cb.model.dto.ContentMetaResponse;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.service.BoardService;
import com.ikiningyou.cb.util.BoardNameMap;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Slf4j
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

  @GetMapping("/comment/amount")
  public ResponseEntity<Integer> getCommentAmountByContentId(
    @RequestParam("id") Long id
  ) {
    Long amount = boardService.getCommentAmountByContentId(id);
    int intSize = Long
      .valueOf(Optional.ofNullable(amount).orElse(0L))
      .intValue();
    return ResponseEntity.ok().body(intSize);
  }
}
