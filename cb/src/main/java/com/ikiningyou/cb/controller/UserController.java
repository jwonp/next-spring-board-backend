package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.UserRequest;
import com.ikiningyou.cb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/registed")
  public ResponseEntity<String> registed(
    @RequestParam("id") String id,
    @RequestParam("provider") String provider,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    Object o = request.getAttribute("_csrf");
    CsrfToken token = (CsrfToken) o;
    String CsrfToken = token.getToken();

    boolean isUserRegisted = userService.isUserRegisted(id, provider);
    log.info("registed CsrfToken : {}, id: {}", CsrfToken, id);

    return ResponseEntity.status(200).body(isUserRegisted ? CsrfToken : null);
  }

  @PostMapping("/register")
  public ResponseEntity<Boolean> addUser(@RequestBody UserRequest userRequest) {
    log.info("regist {}", userRequest.getName());
    short statusCode = 200;
    Boolean isSaved = userService.addUser(userRequest);
    if (isSaved == false) {
      statusCode = 400;
    }
    log.info("statusCode {} isSaved {}", statusCode, isSaved);

    return ResponseEntity.status(statusCode).body(isSaved);
  }
}
