package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;
import com.example.demo.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("showtimes", showtimeService.getAllShowtimes());
        return "bookings/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("showtimes", showtimeService.getAllShowtimes());
        return "bookings/add";
    }

    @PostMapping("/add")
    public String addBooking(@ModelAttribute Booking booking) {
        bookingService.addBooking(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("showtimes", showtimeService.getAllShowtimes());
        return "bookings/edit";
    }

    @PostMapping("/edit")
    public String updateBooking(@ModelAttribute Booking booking) {
        bookingService.updateBooking(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings";
    }
}