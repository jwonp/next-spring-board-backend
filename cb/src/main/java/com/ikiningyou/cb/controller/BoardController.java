package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.ContentListResponse;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.model.dto.SearchResponse;
import com.ikiningyou.cb.service.BoardService;
import com.ikiningyou.cb.util.BoardNameMap;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<ContentMeta[]> getContentListByBoard(
    @RequestParam("board") String board,
    @RequestParam("index") int index
  ) {
    log.info("board {} index {}", board, index);

    Pageable pageable = PageRequest.of(index, 10);
    Optional<List<ContentMeta>> contentMetaList = boardService.getContentListByBoard(
      board,
      pageable
    );
    log.info("is empty ? {}", contentMetaList.get().isEmpty());
    if (contentMetaList.isEmpty()) {
      return ResponseEntity.status(201).body(null);
    }

    return ResponseEntity
      .ok()
      .body(
        contentMetaList
          .get()
          .toArray(new ContentMeta[contentMetaList.get().size()])
      );
  }

  @GetMapping("/size")
  public ResponseEntity<Integer> getSizeByBoard(
    @RequestParam("board") String board
  ) {
    if (boardNameMap.isBoardName(board) == false) {
      return ResponseEntity.status(201).body(-1);
    }
    Long size = boardService.getSizeByBoard(board);
    int intSize = Long.valueOf(Optional.ofNullable(size).orElse(0L)).intValue();
    return ResponseEntity.status(200).body(intSize);
  }

  @GetMapping("/search")
  public ResponseEntity<SearchResponse[]> searchByBoard(
    @RequestParam("search") String search,
    @RequestParam("board") String board
  ) {
    List<SearchResponse> resultList = boardService.searchByBoard(board, search);
    return ResponseEntity
      .status(200)
      .body(resultList.toArray(new SearchResponse[resultList.size()]));
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
