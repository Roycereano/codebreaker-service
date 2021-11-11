package edu.cnm.deepdive.codebreakerservice.controller;

import edu.cnm.deepdive.codebreakerservice.model.entity.User;
import edu.cnm.deepdive.codebreakerservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService service;


  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me() {
    return service.getCurrentUser();
  }
}