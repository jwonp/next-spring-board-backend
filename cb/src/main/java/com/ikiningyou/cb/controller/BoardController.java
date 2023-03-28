package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.service.HelloService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

  @GetMapping("/search")
  public ResponseEntity<?> searchBytarget(
    // HttpServletRequest request,
    @RequestParam("target") String target
    // HttpServletResponse response
  ) {
    // String target = request.getParameter("target");
    log.info("target is {}", target);
    return ResponseEntity.status(200).body(target);
  }
}
