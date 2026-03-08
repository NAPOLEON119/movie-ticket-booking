package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    // Get all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movie by ID
    public Movie getMovieById(String id) {
        return movieRepository.findById(id);
    }

    // Add new movie
    public void addMovie(Movie movie) {
        movie.setId(UUID.randomUUID().toString());
        List<Movie> movies = movieRepository.findAll();
        movies.add(movie);
        movieRepository.saveAll(movies);
    }

    // Update movie
    public void updateMovie(Movie updatedMovie) {
        List<Movie> movies = movieRepository.findAll();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(updatedMovie.getId())) {
                movies.set(i, updatedMovie);
                break;
            }
        }
        movieRepository.saveAll(movies);
    }

    // Delete movie
    public void deleteMovie(String id) {
        List<Movie> movies = movieRepository.findAll();
        movies.removeIf(movie -> movie.getId().equals(id));
        movieRepository.saveAll(movies);
    }
}