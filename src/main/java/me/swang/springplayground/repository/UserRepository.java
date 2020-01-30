package me.swang.springplayground.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.swang.springplayground.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}