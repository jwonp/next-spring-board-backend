package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.UserRequest;
import com.ikiningyou.cb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/registed")
  public ResponseEntity<Boolean> login(
    @RequestParam("id") String id,
    @RequestParam("provider") String provider
  ) {
    boolean isUserRegisted = userService.isUserRegisted(id, provider);
    return ResponseEntity.status(200).body(isUserRegisted);
  }

  @PostMapping("/register")
  public ResponseEntity<Boolean> addUser(@RequestBody UserRequest userRequest) {
    Boolean isSaved = userService.addUser(userRequest);
    int statusCode = isSaved ? 200 : 400;

    return ResponseEntity.status(statusCode).body(isSaved);
  }
}
