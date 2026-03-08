package com.example.demo.model;

public class Booking {
    private String id;
    private String userId;
    private String showtimeId;
    private int numberOfSeats;
    private String seatNumbers;
    private double totalPrice;
    private String bookingDate;
    private String status;

    public Booking() {
    }

    public Booking(String id, String userId, String showtimeId, int numberOfSeats, String seatNumbers, double totalPrice, String bookingDate, String status) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.numberOfSeats = numberOfSeats;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}