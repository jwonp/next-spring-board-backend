package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import com.ikiningyou.cb.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mypage")
public class MyPageController {

  private MyPageService myPageService;

  @GetMapping("/content/liked")
  public ResponseEntity<ContentMetaResponse[]> getLikedContents(
    @RequestParam("user") String userId
  ) {
    return ResponseEntity.status(200).body(null);
  }

  @GetMapping("/content/written")
  public ResponseEntity<ContentMetaResponse[]> getWrittenContents(
    @RequestParam("user") String userId
  ) {
    return ResponseEntity.status(200).body(null);
  }
}
