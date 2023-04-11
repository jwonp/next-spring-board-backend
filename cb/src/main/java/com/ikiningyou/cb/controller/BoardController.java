package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.ContentRequest;
import lombok.extern.slf4j.Slf4j;
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

  @GetMapping("/search")
  public ResponseEntity<?> searchBytarget(
    @RequestParam("target") String target
  ) {
    log.info("target is {}", target);
    return ResponseEntity.status(200).body(target);
  }

  @PostMapping("/edit")
  public ResponseEntity<String> saveContent(@RequestBody ContentRequest dto) {
    log.info(" title: {}, content : {} ", dto.getTitle(), dto.getContent());
    return ResponseEntity.ok().body("good");
  }
}
