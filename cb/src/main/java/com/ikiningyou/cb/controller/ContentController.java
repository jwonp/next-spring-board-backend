package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.content.ContentFullData;
import com.ikiningyou.cb.model.dto.content.ContentModifiedRequest;
import com.ikiningyou.cb.model.dto.content.ContentRequest;
import com.ikiningyou.cb.model.dto.content.ContentShortResponse;
import com.ikiningyou.cb.service.ContentService;
import com.ikiningyou.cb.util.BoardNameMap;
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
@RequestMapping("/content")
public class ContentController {

  @Autowired
  private ContentService contentService;

  private BoardNameMap boardNameMap;

  private ContentController() {
    this.boardNameMap = new BoardNameMap();
  }

  @GetMapping
  public ResponseEntity<ContentFullData> getContentByContentId(
    @RequestParam("id") Long id
  ) {
    ContentFullData content = contentService.getContentById(id);
    if (content == null) {
      return ResponseEntity.status(201).body(null);
    }
    return ResponseEntity.ok().body(content);
  }

  @GetMapping("/short")
  public ResponseEntity<ContentShortResponse> getContentShortByContentId(
    @RequestParam("id") Long contentId
  ) {
    ContentShortResponse contentShort = contentService.getContentShortByContentId(
      contentId
    );
    if (contentShort == null) return ResponseEntity.status(201).body(null);
    return ResponseEntity.status(200).body(contentShort);
  }

  @DeleteMapping
  public ResponseEntity<Boolean> deleteContent(
    @RequestParam("content") Long contentId,
    @RequestParam("user") String userId
  ) {
    boolean isDeleted = contentService.deleteContent(contentId, userId);
    return ResponseEntity.status(isDeleted ? 200 : 201).body(null);
  }

  @GetMapping("/author")
  public ResponseEntity<Boolean> isAuthorByContentId(
    @RequestParam("content") Long contentId,
    @RequestParam("author") String author
  ) {
    boolean isAuthor = contentService.isAuthorByContentId(contentId, author);
    return ResponseEntity.status(200).body(isAuthor);
  }

  @PostMapping("/edit")
  public ResponseEntity<Long> saveContent(@RequestBody ContentRequest content) {
    Long savedContentId = -1l;
    Boolean isBoardNameCorrect = false;
    Boolean isSuccessd = false;

    if (boardNameMap.isBoardName(content.getBoard())) {
      isBoardNameCorrect = true;
      savedContentId = contentService.addContent(content);
    }
    isSuccessd = savedContentId > 0l && isBoardNameCorrect;

    return ResponseEntity.status(isSuccessd ? 200 : 400).body(savedContentId);
  }

  @PatchMapping("/modify")
  public ResponseEntity<Long> modifyContent(
    @RequestBody ContentModifiedRequest contentModifiedRequest
  ) {
    Long contentId = contentModifiedRequest.getContentId();
    String title = contentModifiedRequest.getTitle();
    String contents = contentModifiedRequest.getContents();
    String author = contentModifiedRequest.getAuthor();

    Long modifiedContentId = contentService.modifyContent(
      contentId,
      title,
      contents,
      author
    );
    return ResponseEntity
      .status(modifiedContentId > 0l ? 200 : 201)
      .body(modifiedContentId);
  }
}
