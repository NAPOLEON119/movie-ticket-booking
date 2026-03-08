package com.example.demo.model;

public class Theater {
    private String id;
    private String name;
    private int capacity;
    private String type;

    // Constructor
    public Theater() {
    }

    public Theater(String id, String name, int capacity, String type) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}