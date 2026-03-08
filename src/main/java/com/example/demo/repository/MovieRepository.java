package com.example.demo.repository;

import com.example.demo.model.Movie;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {
    private final String FILE_PATH = "data/movies.txt";

    // Save all movies to file
    public void saveAll(List<Movie> movies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Movie movie : movies) {
                writer.write(movie.getId() + "|" + 
                           movie.getTitle() + "|" + 
                           movie.getGenre() + "|" + 
                           movie.getDuration() + "|" + 
                           movie.getLanguage() + "|" + 
                           movie.getDescription() + "|" +
                           (movie.getImageUrl() != null ? movie.getImageUrl() : "") + "|" +
                           (movie.getTrailerUrl() != null ? movie.getTrailerUrl() : ""));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all movies from file
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return movies;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    Movie movie = new Movie(
                        parts[0], // id
                        parts[1], // title
                        parts[2], // genre
                        Integer.parseInt(parts[3]), // duration
                        parts[4], // language
                        parts[5], // description
                        parts.length > 6 ? parts[6] : "",  // imageUrl
                        parts.length > 7 ? parts[7] : ""   // trailerUrl ★ NEW
                    );
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Find movie by ID
    public Movie findById(String id) {
        List<Movie> movies = findAll();
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }
}