package com.example.demo.model;

public class Movie {
    private String id;
    private String title;
    private String genre;
    private int duration;
    private String language;
    private String description;
    private String imageUrl;
    private String trailerUrl;  // ★ NEW FIELD

    // Constructor
    public Movie() {
    }

    public Movie(String id, String title, String genre, int duration, String language, String description, String imageUrl, String trailerUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.description = description;
        this.imageUrl = imageUrl;
        this.trailerUrl = trailerUrl;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ★ NEW GETTER & SETTER ★
    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }
}