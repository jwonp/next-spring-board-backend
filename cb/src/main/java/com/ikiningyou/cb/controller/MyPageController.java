package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mypage")
public class MyPageController {

  @Autowired
  private MyPageService myPageService;

  @GetMapping("/content/liked")
  public ResponseEntity<ContentMetaResponse[]> getLikedContents(
    @RequestParam("user") String userId,
    @RequestParam(required = false, value = "index") String index
  ) {
    ContentMetaResponse[] likedContents = myPageService.getLikedContents(
      userId,
      index
    );
    if (likedContents == null) {
      return ResponseEntity.status(400).body(null);
    }
    return ResponseEntity.status(200).body(likedContents);
  }

  @GetMapping("/content/written")
  public ResponseEntity<ContentMetaResponse[]> getWrittenContents(
    @RequestParam("user") String userId,
    @RequestParam(required = false, value = "index") String index
  ) {
    ContentMetaResponse[] writtenContents = myPageService.getWrittenContents(
      userId,
      index
    );
    if (writtenContents == null) {
      return ResponseEntity.status(400).body(null);
    }
    return ResponseEntity.status(200).body(writtenContents);
  }
}
