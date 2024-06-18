package com.test2.test2.controllers;

import com.test2.test2.entities.User;
import com.test2.test2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user){
       User newUser =  userService.createUser(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User userToFind = userService.searchById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(userToFind);
    }
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.searchAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user){
    userService.updateUserById(id, user);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        User userToDelete = userService.deleteUserById(id);{
            return ResponseEntity.ok().body(userToDelete);
        }
    }
}
