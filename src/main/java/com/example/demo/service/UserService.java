package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(String id) {
        return userRepository.findById(id);
    }

    // Add new user
    public void addUser(User user) {
        user.setId(UUID.randomUUID().toString());
        List<User> users = userRepository.findAll();
        users.add(user);
        userRepository.saveAll(users);
    }

    // Update user
    public void updateUser(User updatedUser) {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break;
            }
        }
        userRepository.saveAll(users);
    }

    // Delete user
    public void deleteUser(String id) {
        List<User> users = userRepository.findAll();
        users.removeIf(user -> user.getId().equals(id));
        userRepository.saveAll(users);
    }
    public User findByEmail(String email) {
    return userRepository.findByEmail(email);
}

public User authenticate(String email, String password) {
    User user = findByEmail(email);
    if (user != null && user.getPassword().equals(password)) {
        return user;
    }
    return null;
}

public boolean emailExists(String email) {
    return userRepository.emailExists(email);
}
}