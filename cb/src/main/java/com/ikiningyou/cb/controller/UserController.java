package com.ikiningyou.cb.controller;

import com.ikiningyou.cb.model.dto.UserRequest;
import com.ikiningyou.cb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
    userService.addUser(userRequest);
    return ResponseEntity.ok().body("good");
  }
}
