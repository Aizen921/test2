package com.test2.test2.services;

import com.test2.test2.DAO.UserDAO;
import com.test2.test2.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User createUser(User user){
        User newUser = userDAO.save(user);
        return newUser;
    }
    public User updateUserById(Long id, User user){
        User newUser = userDAO.findById(id).orElse(null);
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setAge(user.getAge());
        userDAO.save(newUser);
        return newUser;
    }
    public User searchById(Long id){
        User userToFind = userDAO.findById(id).orElse(null);
        return userToFind;
    }
    public List<User> searchAllUser(){
        return userDAO.findAll();
    }
    public User deleteUserById(Long id){
        User userToDelete = userDAO.findById(id).orElse(null);
        userDAO.deleteById(id);
        return userToDelete;
    }
    public void deleteAllUsers(){
        userDAO.deleteAll();
    }
}
