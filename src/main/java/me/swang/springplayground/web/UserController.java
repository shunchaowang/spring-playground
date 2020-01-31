package me.swang.springplayground.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import me.swang.springplayground.domain.User;
import me.swang.springplayground.service.UserService;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private String createdOnStr = "01/30/2020 15:00:00.123";
  private String dateFormatStr = "MM/dd/yyyy HH:mm:ss.SSS";

  @GetMapping("/users")
  private List<User> getAllUsers() {
    // Arrays.stream(TimeZone.getAvailableIDs()).forEach(id ->
    // logger.debug("TimeZone id {}", id));
    logger.debug("Default time zone id {} and name {}", TimeZone.getDefault().getID(),
        TimeZone.getDefault().getDisplayName());
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  private User getUser(@PathVariable("id") long id) {
    Optional<User> userOptional = userService.getUserById(id);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      // read updateOn as Central time zone
      Calendar updatedOn = user.getUpdatedOn();
      logger.debug("UpdatedOn in default TimeZone {}", updatedOn);
      Calendar cstCalendar = Calendar.getInstance(TimeZone.getTimeZone("US/Central"));
      cstCalendar.setTime(updatedOn.getTime());
      logger.debug("UpdatedOn in Central TimeZone {}", cstCalendar);
      user.setUpdatedOn(cstCalendar);

      return user;
    }
    return null;
  }

  @PostMapping("/users")
  private long saveUser(@RequestBody User user) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
    Date date = null;
    try {
      date = dateFormat.parse(createdOnStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    // set createdOn of Timestamp
    user.setCreatedOn(new Timestamp(date.getTime()));
    // set updatedOn as EST of Calendar
    TimeZone estTimeZone = TimeZone.getTimeZone("US/Eastern");
    Calendar updateOn = Calendar.getInstance(estTimeZone);
    updateOn.setTime(date);
    user.setUpdatedOn(updateOn);
    userService.saveOrUpdate(user);
    return user.getId();
  }

  @PutMapping("/users")
  private long updateUser(@RequestBody User user) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
    Date date = null;
    try {
      date = dateFormat.parse(createdOnStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    // set updatedOn as EST of Calendar
    TimeZone estTimeZone = TimeZone.getTimeZone("US/Eastern");
    Calendar updateOn = Calendar.getInstance(estTimeZone);
    updateOn.setTime(date);
    user.setUpdatedOn(updateOn);
    userService.saveOrUpdate(user);
    return user.getId();
  }

  @DeleteMapping("/users/{id}")
  private void deleteUser(@PathVariable("id") long id) {
    userService.delete(id);
  }
}