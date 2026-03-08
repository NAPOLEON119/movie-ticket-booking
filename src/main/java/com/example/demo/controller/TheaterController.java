package com.example.demo.controller;

import com.example.demo.model.Theater;
import com.example.demo.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    // Show all theaters
    @GetMapping
    public String listTheaters(Model model) {
        model.addAttribute("theaters", theaterService.getAllTheaters());
        return "theaters/list";
    }

    // Show add theater form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("theater", new Theater());
        return "theaters/add";
    }

    // Save new theater
    @PostMapping("/add")
    public String addTheater(@ModelAttribute Theater theater) {
        theaterService.addTheater(theater);
        return "redirect:/theaters";
    }

    // Show edit theater form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Theater theater = theaterService.getTheaterById(id);
        model.addAttribute("theater", theater);
        return "theaters/edit";
    }

    // Update theater
    @PostMapping("/edit")
    public String updateTheater(@ModelAttribute Theater theater) {
        theaterService.updateTheater(theater);
        return "redirect:/theaters";
    }

    // Delete theater
    @GetMapping("/delete/{id}")
    public String deleteTheater(@PathVariable String id) {
        theaterService.deleteTheater(id);
        return "redirect:/theaters";
    }
}