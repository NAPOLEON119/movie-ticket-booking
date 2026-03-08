package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Show all movies
    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movies/list";
    }

    // Show add movie form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movies/add";
    }

    // Save new movie
    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/movies";
    }

    // Show edit movie form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movies/edit";
    }

    // Update movie
    @PostMapping("/edit")
    public String updateMovie(@ModelAttribute Movie movie) {
        movieService.updateMovie(movie);
        return "redirect:/movies";
    }

    // Delete movie
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
}