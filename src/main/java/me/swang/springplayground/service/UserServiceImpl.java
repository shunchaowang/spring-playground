package me.swang.springplayground.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.swang.springplayground.domain.User;
import me.swang.springplayground.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    userRepository.findAll().forEach(user -> users.add(user));
    return users;
  }

  @Override
  public Optional<User> getUserById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public void saveOrUpdate(User user) {
    userRepository.save(user);
  }

  @Override
  public void delete(long id) {
    userRepository.deleteById(id);
  }

}