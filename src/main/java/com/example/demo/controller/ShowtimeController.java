package com.example.demo.controller;

import com.example.demo.model.Showtime;
import com.example.demo.service.ShowtimeService;
import com.example.demo.service.MovieService;
import com.example.demo.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public String listShowtimes(Model model) {
        model.addAttribute("showtimes", showtimeService.getAllShowtimes());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "showtimes/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("showtime", new Showtime());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "showtimes/add";
    }

    @PostMapping("/add")
    public String addShowtime(@ModelAttribute Showtime showtime) {
        showtimeService.addShowtime(showtime);
        return "redirect:/showtimes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        model.addAttribute("showtime", showtime);
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "showtimes/edit";
    }

    @PostMapping("/edit")
    public String updateShowtime(@ModelAttribute Showtime showtime) {
        showtimeService.updateShowtime(showtime);
        return "redirect:/showtimes";
    }

    @GetMapping("/delete/{id}")
    public String deleteShowtime(@PathVariable String id) {
        showtimeService.deleteShowtime(id);
        return "redirect:/showtimes";
    }
}