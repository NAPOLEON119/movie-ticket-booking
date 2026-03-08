package com.example.demo.repository;

import com.example.demo.model.Showtime;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShowtimeRepository {
    private final String FILE_PATH = "data/showtimes.txt";

    public void saveAll(List<Showtime> showtimes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Showtime showtime : showtimes) {
                writer.write(showtime.getId() + "|" + 
                           showtime.getMovieId() + "|" + 
                           showtime.getTheaterId() + "|" + 
                           showtime.getShowDate() + "|" + 
                           showtime.getShowTime() + "|" + 
                           showtime.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Showtime> findAll() {
        List<Showtime> showtimes = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return showtimes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    Showtime showtime = new Showtime(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        Double.parseDouble(parts[5])
                    );
                    showtimes.add(showtime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    public Showtime findById(String id) {
        List<Showtime> showtimes = findAll();
        for (Showtime showtime : showtimes) {
            if (showtime.getId().equals(id)) {
                return showtime;
            }
        }
        return null;
    }
}