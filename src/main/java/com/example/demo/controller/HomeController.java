package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    // ==================== EXISTING PAGES ====================

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "home/index";
    }

    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable String id, Model model, HttpSession session) {
        Movie movie = movieService.getMovieById(id);

        List<Showtime> allShowtimes = showtimeService.getAllShowtimes();
        List<Showtime> movieShowtimes = allShowtimes.stream()
                .filter(s -> s.getMovieId().equals(id))
                .collect(Collectors.toList());

        model.addAttribute("movie", movie);
        model.addAttribute("showtimes", movieShowtimes);
        model.addAttribute("theaters", theaterService.getAllTheaters());
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "home/movie-details";
    }

    @GetMapping("/book/{showtimeId}")
    public String seatSelection(@PathVariable String showtimeId, Model model, HttpSession session) {
        System.out.println("========================================");
        System.out.println("🎫 SEAT SELECTION CALLED!");
        System.out.println("Showtime ID received: " + showtimeId);
        System.out.println("========================================");

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            System.out.println("❌ User not logged in! Redirecting to login...");
            return "redirect:/login?redirect=/book/" + showtimeId;
        }

        try {
            Showtime showtime = showtimeService.getShowtimeById(showtimeId);
            System.out.println("Showtime found: " + (showtime != null));

            if (showtime == null) {
                System.out.println("❌ Showtime is NULL! Redirecting...");
                return "redirect:/";
            }

            Movie movie = movieService.getMovieById(showtime.getMovieId());
            System.out.println("Movie found: " + (movie != null));

            Theater theater = theaterService.getTheaterById(showtime.getTheaterId());
            System.out.println("Theater found: " + (theater != null));

            List<Booking> allBookings = bookingService.getAllBookings();
            System.out.println("Total bookings: " + allBookings.size());

            String bookedSeatsString = "";
            for (Booking booking : allBookings) {
                if (booking.getShowtimeId() != null && booking.getShowtimeId().equals(showtimeId)) {
                    if (!"CANCELLED".equals(booking.getStatus())) {
                        if (!bookedSeatsString.isEmpty()) {
                            bookedSeatsString += ",";
                        }
                        bookedSeatsString += booking.getSeatNumbers();
                    }
                }
            }
            System.out.println("Booked seats: " + bookedSeatsString);

            model.addAttribute("showtime", showtime);
            model.addAttribute("movie", movie);
            model.addAttribute("theater", theater);
            model.addAttribute("bookedSeatsString", bookedSeatsString);
            model.addAttribute("loggedInUser", loggedInUser);

            System.out.println("✅ Returning template: home/seat-selection");
            return "home/seat-selection";

        } catch (Exception e) {
            System.err.println("❌ ERROR in seat selection:");
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping("/book/confirm")
    @ResponseBody
    public String confirmBooking(@RequestParam String showtimeId,
                                 @RequestParam String seatNumbers,
                                 @RequestParam int numberOfSeats,
                                 @RequestParam double totalPrice,
                                 HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "error: Please login first!";
        }

        try {
            Booking booking = new Booking();
            booking.setUserId(loggedInUser.getId());
            booking.setShowtimeId(showtimeId);
            booking.setNumberOfSeats(numberOfSeats);
            booking.setSeatNumbers(seatNumbers);
            booking.setTotalPrice(totalPrice);

            bookingService.addBooking(booking);

            // Get the booking ID after saving
            List<Booking> allBookings = bookingService.getAllBookings();
            String bookingId = "";
            for (int i = allBookings.size() - 1; i >= 0; i--) {
                Booking b = allBookings.get(i);
                if (b.getUserId().equals(loggedInUser.getId()) &&
                    b.getShowtimeId().equals(showtimeId) &&
                    b.getSeatNumbers().equals(seatNumbers)) {
                    bookingId = b.getId();
                    break;
                }
            }

            System.out.println("✅ Booking saved by user: " + loggedInUser.getName() + " | Seats: " + seatNumbers + " | ID: " + bookingId);

            return "success:" + bookingId;
        } catch (Exception e) {
            System.err.println("❌ Booking failed: " + e.getMessage());
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

    // ==================== AUTH PAGES ====================

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String success,
                            @RequestParam(required = false) String redirect,
                            Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        model.addAttribute("error", error);
        model.addAttribute("success", success);
        model.addAttribute("redirect", redirect != null ? redirect : "");
        return "home/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam(required = false, defaultValue = "") String redirect,
                        HttpSession session,
                        Model model) {
        User user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            System.out.println("✅ User logged in: " + user.getName() + " (" + user.getRole() + ")");

            if (!redirect.isEmpty()) {
                return "redirect:" + redirect;
            }
            if ("ADMIN".equals(user.getRole())) {
                 return "redirect:/movies";
                }
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid email or password!");
        model.addAttribute("redirect", redirect);
        return "home/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        return "home/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords don't match!");
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            return "home/register";
        }

        if (userService.emailExists(email)) {
            model.addAttribute("error", "Email already registered!");
            model.addAttribute("name", name);
            model.addAttribute("phone", phone);
            return "home/register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole("CUSTOMER");
        userService.addUser(user);

        System.out.println("✅ New user registered: " + name + " (" + email + ")");

        return "redirect:/login?success=Registration successful! Please login.";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            System.out.println("👋 User logged out: " + user.getName());
        }
        session.invalidate();
        return "redirect:/";
    }

    // ==================== MY BOOKINGS ====================

    @GetMapping("/my-bookings")
    public String myBookings(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login?redirect=/my-bookings";
        }

        List<Booking> userBookings = bookingService.getAllBookings().stream()
                .filter(b -> b.getUserId().equals(loggedInUser.getId()))
                .collect(Collectors.toList());

        java.util.Map<String, Movie> movieMap = new java.util.HashMap<>();
        for (Movie m : movieService.getAllMovies()) {
            movieMap.put(m.getId(), m);
        }

        java.util.Map<String, Showtime> showtimeMap = new java.util.HashMap<>();
        for (Showtime s : showtimeService.getAllShowtimes()) {
            showtimeMap.put(s.getId(), s);
        }

        java.util.Map<String, Theater> theaterMap = new java.util.HashMap<>();
        for (Theater t : theaterService.getAllTheaters()) {
            theaterMap.put(t.getId(), t);
        }

        model.addAttribute("bookings", userBookings);
        model.addAttribute("movieMap", movieMap);
        model.addAttribute("showtimeMap", showtimeMap);
        model.addAttribute("theaterMap", theaterMap);
        model.addAttribute("loggedInUser", loggedInUser);

        return "home/my-bookings";
    }

    // ==================== CANCEL BOOKING ====================

    @PostMapping("/my-bookings/cancel/{id}")
    public String cancelBooking(@PathVariable String id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Booking> allBookings = bookingService.getAllBookings();
        for (int i = 0; i < allBookings.size(); i++) {
            Booking booking = allBookings.get(i);
            if (booking.getId().equals(id) && booking.getUserId().equals(loggedInUser.getId())) {
                booking.setStatus("CANCELLED");
                allBookings.set(i, booking);
                break;
            }
        }

        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("data/bookings.txt"));
            for (Booking b : allBookings) {
                writer.write(b.getId() + "|" + b.getUserId() + "|" + b.getShowtimeId() + "|" +
                        b.getNumberOfSeats() + "|" + b.getSeatNumbers() + "|" + b.getTotalPrice() + "|" +
                        b.getBookingDate() + "|" + b.getStatus());
                writer.newLine();
            }
            writer.close();
            System.out.println("✅ Booking cancelled: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/my-bookings";
    }

    // ==================== BOOKING RECEIPT ====================

    @GetMapping("/booking-receipt/{bookingId}")
    public String bookingReceipt(@PathVariable String bookingId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Booking booking = null;
        for (Booking b : bookingService.getAllBookings()) {
            if (b.getId().equals(bookingId) && b.getUserId().equals(loggedInUser.getId())) {
                booking = b;
                break;
            }
        }

        if (booking == null) {
            return "redirect:/my-bookings";
        }

        Showtime showtime = showtimeService.getShowtimeById(booking.getShowtimeId());
        Movie movie = showtime != null ? movieService.getMovieById(showtime.getMovieId()) : null;
        Theater theater = showtime != null ? theaterService.getTheaterById(showtime.getTheaterId()) : null;

        model.addAttribute("booking", booking);
        model.addAttribute("showtime", showtime);
        model.addAttribute("movie", movie);
        model.addAttribute("theater", theater);
        model.addAttribute("loggedInUser", loggedInUser);

        return "home/booking-receipt";
    }

    // ==================== ADMIN DASHBOARD ====================

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            return "redirect:/login?error=Admin access required!";
        }

        List<Movie> allMovies = movieService.getAllMovies();
        List<User> allUsers = userService.getAllUsers();
        List<Theater> allTheaters = theaterService.getAllTheaters();
        List<Showtime> allShowtimes = showtimeService.getAllShowtimes();
        List<Booking> allBookings = bookingService.getAllBookings();

        long confirmedBookings = allBookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count();
        long cancelledBookings = allBookings.stream().filter(b -> "CANCELLED".equals(b.getStatus())).count();
        double totalRevenue = allBookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(Booking::getTotalPrice)
                .sum();
        long totalCustomers = allUsers.stream().filter(u -> "CUSTOMER".equals(u.getRole())).count();

        model.addAttribute("totalMovies", allMovies.size());
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("totalTheaters", allTheaters.size());
        model.addAttribute("totalShowtimes", allShowtimes.size());
        model.addAttribute("totalBookings", allBookings.size());
        model.addAttribute("confirmedBookings", confirmedBookings);
        model.addAttribute("cancelledBookings", cancelledBookings);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("loggedInUser", loggedInUser);

        return "home/admin-dashboard";
    }
}