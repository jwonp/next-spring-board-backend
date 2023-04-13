package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.ContentListResponse;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.service.BoardService;
import com.ikiningyou.cb.util.BoardNameMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

  @Autowired
  private BoardService boardService;

  private BoardNameMap boardNameMap;

  private BoardController() {
    this.boardNameMap = new BoardNameMap();
  }

  @GetMapping("/list")
  public ResponseEntity<ContentListResponse[]> getContentListByBoard(
    @RequestParam("board") String board,
    @RequestParam("index") int index
  ) {
    Optional<ContentListResponse> contentList = boardService.
    return 
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchBytarget(
    @RequestParam("target") String target
  ) {
    log.info("target is {}", target);
    return ResponseEntity.status(200).body(target);
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
}
