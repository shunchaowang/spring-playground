package me.swang.springplayground.service;

import java.util.List;
import java.util.Optional;

import me.swang.springplayground.domain.User;

public interface UserService {
  List<User> getAllUsers();

  Optional<User> getUserById(long id);

  void saveOrUpdate(User user);

  void delete(long id);
}