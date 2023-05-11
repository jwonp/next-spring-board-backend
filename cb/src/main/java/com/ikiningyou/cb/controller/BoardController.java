package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.service.BoardService;
import com.ikiningyou.cb.util.BoardNameMap;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @RequestParam("board") String board,
    @RequestParam(value = "index", required = false) Optional<Integer> index
  ) {
    int pageableIndex = 0;
    if (index.isPresent()) {
      pageableIndex = index.get();
    }
    ContentMetaResponse[] resultList = boardService.searchByQueryAndBoard(
      board,
      search,
      pageableIndex
    );
    if (resultList == null) {
      return ResponseEntity.status(201).body(null);
    }
    return ResponseEntity.status(200).body(resultList);
  }

  @GetMapping("likest")
  public ResponseEntity<ContentMetaResponse[]> getMostLikedContents() {
    return ResponseEntity.status(200).body(null);
  }

  @GetMapping("viewest")
  public ResponseEntity<ContentMetaResponse[]> getMostViewedContents() {
    return ResponseEntity.status(200).body(null);
  }

  @GetMapping("recent")
  public ResponseEntity<ContentMetaResponse[]> getRecentlyContents() {
    return ResponseEntity.status(200).body(null);
  }
}
