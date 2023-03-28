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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

  @Autowired
  private HelloService helloService;

  @GetMapping("/hello")
  public ResponseEntity<?> getHello(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    String identifier = request.getHeader("X-IDENTIFIER");
    String token = helloService.getTokenByIdentifier(identifier);
    log.info("getHello has token is {}", token);
    // cookie로 보낼 경우
    // Cookie cookie = new Cookie("X-CSRF-TOKEN", token);
    // cookie.setMaxAge(3 * 24 * 60 * 60); // 3day * 24hour * 60 min * 60 sec
    // cookie.setHttpOnly(true);
    // cookie.setSecure(true);
    // cookie.setPath("/");
    // response.addCookie(cookie);
    // if (token == null) return ResponseEntity.status(400).build();
    // return ResponseEntity.ok().build();.header("X-CSRF-TOKEN", token)
    if (token == null) return ResponseEntity.status(400).build();
    return ResponseEntity.status(200).build();
  }

  @PostMapping("/hello")
  public String postHello() {
    return "hello";
  }
}
