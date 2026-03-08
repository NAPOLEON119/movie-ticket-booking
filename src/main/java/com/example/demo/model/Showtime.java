package com.example.demo.model;

public class Showtime {
    private String id;
    private String movieId;
    private String theaterId;
    private String showDate;
    private String showTime;
    private double price;

    public Showtime() {
    }

    public Showtime(String id, String movieId, String theaterId, String showDate, String showTime, double price) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}