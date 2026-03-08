package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public void addBooking(Booking booking) {
        booking.setId(UUID.randomUUID().toString());
        booking.setBookingDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        booking.setStatus("Confirmed");
        List<Booking> bookings = bookingRepository.findAll();
        bookings.add(booking);
        bookingRepository.saveAll(bookings);
    }

    public void updateBooking(Booking updatedBooking) {
        List<Booking> bookings = bookingRepository.findAll();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(updatedBooking.getId())) {
                bookings.set(i, updatedBooking);
                break;
            }
        }
        bookingRepository.saveAll(bookings);
    }

    public void deleteBooking(String id) {
        List<Booking> bookings = bookingRepository.findAll();
        bookings.removeIf(booking -> booking.getId().equals(id));
        bookingRepository.saveAll(bookings);
    }
}