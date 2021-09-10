package com.odyssey.pretest.user;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private ArrayList<User> users = new ArrayList<>();
  private Long currentUniqueId = 1L;

  @GetMapping
  public ArrayList<User> getAllUsers() {
    return users;
  }

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable("userId") Long id) {
    int index = 0;
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId() == id) {
        index = i;
      }
    }
    return users.get(index);
  }

  @PostMapping
  public ResponseEntity<User> createNewUser(@RequestBody User user) {
    user.setId(currentUniqueId);
    users.add(user);
    currentUniqueId++;
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PutMapping("/{userId}")
  public User updateExistingUser(@PathVariable("userId") Long id, @RequestBody User user) {
    int index = 0;
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId() == id) {
        index = i;
      }
    }

    User updatedUser = users.get(index);
    updatedUser.setfullName(user.getFullName());

    users.set(index, updatedUser);

    return updatedUser;
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteExistingUserById(@PathVariable("userId") Long id) {
    int index = 0; 
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId() == id) {
        index = i;
      }
    }

    users.remove(index);
  }
}
