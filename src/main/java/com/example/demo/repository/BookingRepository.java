package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {
    private final String FILE_PATH = "data/bookings.txt";

    public void saveAll(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Booking booking : bookings) {
                writer.write(booking.getId() + "|" + 
                           booking.getUserId() + "|" + 
                           booking.getShowtimeId() + "|" + 
                           booking.getNumberOfSeats() + "|" + 
                           booking.getSeatNumbers() + "|" + 
                           booking.getTotalPrice() + "|" + 
                           booking.getBookingDate() + "|" + 
                           booking.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    Booking booking = new Booking(
                        parts[0],
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        parts[4],
                        Double.parseDouble(parts[5]),
                        parts[6],
                        parts[7]
                    );
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public Booking findById(String id) {
        List<Booking> bookings = findAll();
        for (Booking booking : bookings) {
            if (booking.getId().equals(id)) {
                return booking;
            }
        }
        return null;
    }
}