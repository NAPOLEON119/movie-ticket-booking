package com.example.demo.service;

import com.example.demo.model.Showtime;
import com.example.demo.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowtimeService {
    
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public Showtime getShowtimeById(String id) {
        return showtimeRepository.findById(id);
    }

    public void addShowtime(Showtime showtime) {
        showtime.setId(UUID.randomUUID().toString());
        List<Showtime> showtimes = showtimeRepository.findAll();
        showtimes.add(showtime);
        showtimeRepository.saveAll(showtimes);
    }

    public void updateShowtime(Showtime updatedShowtime) {
        List<Showtime> showtimes = showtimeRepository.findAll();
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtimes.get(i).getId().equals(updatedShowtime.getId())) {
                showtimes.set(i, updatedShowtime);
                break;
            }
        }
        showtimeRepository.saveAll(showtimes);
    }

    public void deleteShowtime(String id) {
        List<Showtime> showtimes = showtimeRepository.findAll();
        showtimes.removeIf(showtime -> showtime.getId().equals(id));
        showtimeRepository.saveAll(showtimes);
    }
}