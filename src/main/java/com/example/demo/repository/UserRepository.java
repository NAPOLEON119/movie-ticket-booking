package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final String FILE_PATH = "data/users.txt";

    // Save all users to file
    public void saveAll(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getId() + "|" + 
                           user.getName() + "|" + 
                           user.getEmail() + "|" + 
                           user.getPhone() + "|" + 
                           user.getPassword() + "|" + 
                           user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all users from file
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    User user = new User(
                        parts[0], // id
                        parts[1], // name
                        parts[2], // email
                        parts[3], // phone
                        parts[4], // password
                        parts[5]  // role
                    );
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Find user by ID
    public User findById(String id) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public User findByEmail(String email) {
    return findAll().stream()
            .filter(user -> user.getEmail() != null && user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
}

public boolean emailExists(String email) {
    return findByEmail(email) != null;
}
}