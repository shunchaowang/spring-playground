package me.swang.springplayground.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import me.swang.springplayground.domain.User;
import me.swang.springplayground.service.UserService;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/users")
  private List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  private User getUser(@PathVariable("id") long id) {
    return userService.getUserById(id).isPresent() ? userService.getUserById(id).get() : null;
  }

  @PostMapping("/users")
  private long saveUser(@RequestBody User user) {
    userService.saveOrUpdate(user);
    return user.getId();
  }

  @DeleteMapping("/users/{id}")
  private void deleteUser(@PathVariable("id") long id) {
    userService.delete(id);
  }
}