package com.example.demo.service;

import com.example.demo.model.Theater;
import com.example.demo.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TheaterService {
    
    @Autowired
    private TheaterRepository theaterRepository;

    // Get all theaters
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    // Get theater by ID
    public Theater getTheaterById(String id) {
        return theaterRepository.findById(id);
    }

    // Add new theater
    public void addTheater(Theater theater) {
        theater.setId(UUID.randomUUID().toString());
        List<Theater> theaters = theaterRepository.findAll();
        theaters.add(theater);
        theaterRepository.saveAll(theaters);
    }

    // Update theater
    public void updateTheater(Theater updatedTheater) {
        List<Theater> theaters = theaterRepository.findAll();
        for (int i = 0; i < theaters.size(); i++) {
            if (theaters.get(i).getId().equals(updatedTheater.getId())) {
                theaters.set(i, updatedTheater);
                break;
            }
        }
        theaterRepository.saveAll(theaters);
    }

    // Delete theater
    public void deleteTheater(String id) {
        List<Theater> theaters = theaterRepository.findAll();
        theaters.removeIf(theater -> theater.getId().equals(id));
        theaterRepository.saveAll(theaters);
    }
}