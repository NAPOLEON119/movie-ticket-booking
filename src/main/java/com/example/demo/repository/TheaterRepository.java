package com.example.demo.repository;

import com.example.demo.model.Theater;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TheaterRepository {
    private final String FILE_PATH = "data/theaters.txt";

    // Save all theaters to file
    public void saveAll(List<Theater> theaters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Theater theater : theaters) {
                writer.write(theater.getId() + "|" + 
                           theater.getName() + "|" + 
                           theater.getCapacity() + "|" + 
                           theater.getType());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all theaters from file
    public List<Theater> findAll() {
        List<Theater> theaters = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return theaters;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    Theater theater = new Theater(
                        parts[0], // id
                        parts[1], // name
                        Integer.parseInt(parts[2]), // capacity
                        parts[3]  // type
                    );
                    theaters.add(theater);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return theaters;
    }

    // Find theater by ID
    public Theater findById(String id) {
        List<Theater> theaters = findAll();
        for (Theater theater : theaters) {
            if (theater.getId().equals(id)) {
                return theater;
            }
        }
        return null;
    }
}