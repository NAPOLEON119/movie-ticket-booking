package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;

@SpringBootApplication
public class MovieTicketBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketBookingApplication.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            // Create data directory if it doesn't exist
            Path dataDir = Paths.get("data");
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
                System.out.println("✅ Created data directory");
            }

            // Create default files with sample data if they don't exist
            createFileIfNotExists("data/movies.txt", getDefaultMovies());
            createFileIfNotExists("data/users.txt", getDefaultUsers());
            createFileIfNotExists("data/theaters.txt", getDefaultTheaters());
            createFileIfNotExists("data/showtimes.txt", getDefaultShowtimes());
            createFileIfNotExists("data/bookings.txt", "");

            System.out.println("✅ Data files initialized successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error initializing data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createFileIfNotExists(String filePath, String defaultContent) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.writeString(path, defaultContent);
            System.out.println("✅ Created: " + filePath);
        } else if (Files.size(path) == 0 && !defaultContent.isEmpty()) {
            // File exists but is empty, add default content
            Files.writeString(path, defaultContent);
            System.out.println("✅ Populated empty file: " + filePath);
        }
    }

    private String getDefaultMovies() {
        return """
            movie-001|Avengers Endgame|Action|181|English|The epic conclusion to the Infinity Saga. The Avengers assemble once more to undo Thanos' snap and save the universe.|https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg|
            movie-002|Spider-Man No Way Home|Action|148|English|Peter Parker seeks help from Doctor Strange to make the world forget he is Spider-Man, but the spell goes wrong.|https://m.media-amazon.com/images/M/MV5BZWMyYzFjYTYtNTRjYi00OGExLWE2YzgtOGRmYjAxZTU3NzBiXkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_.jpg|
            movie-003|The Batman|Action|176|English|Batman ventures into Gotham City's underworld when a sadistic killer leaves behind a trail of cryptic clues.|https://m.media-amazon.com/images/M/MV5BMDdmMTBiNTYtMDIzNi00NGVlLWIzMDYtZTk3MTQ3NGQxZGEwXkEyXkFqcGdeQXVyMzMwOTU5MDk@._V1_.jpg|
            movie-004|Avatar The Way of Water|Sci-Fi|192|English|Jake Sully lives with his newfound family on Pandora. When a familiar threat returns, Jake must work with Neytiri to protect their home.|https://m.media-amazon.com/images/M/MV5BYjhiNjBlODctY2ZiOC00YjVlLWFlNzAtNTVhNzM1YjI1NzMxXkEyXkFqcGdeQXVyMjQxNTE1MDA@._V1_.jpg|
            movie-005|Top Gun Maverick|Action|130|English|After thirty years, Maverick is still pushing the envelope as a top naval aviator, but must confront ghosts of his past.|https://m.media-amazon.com/images/M/MV5BZWYzOGEwNTgtNWU3NS00ZTQ0LWJkODUtMmVhMjIwMjA1ZmQwXkEyXkFqcGdeQXVyMjkwOTAyMDU@._V1_.jpg|
            """;
    }

    private String getDefaultUsers() {
        return """
            admin-001|Admin User|admin@scope.com|0771234567|admin123|ADMIN
            cust-001|John Doe|john@test.com|0779876543|john123|CUSTOMER
            cust-002|Jane Smith|jane@test.com|0771112222|jane123|CUSTOMER
            """;
    }

    private String getDefaultTheaters() {
        return """
            theater-001|Hall A - Standard|100|Standard
            theater-002|Hall B - IMAX|150|IMAX
            theater-003|Hall C - Premium|80|Premium
            theater-004|Hall D - 4DX|60|4DX
            """;
    }

    private String getDefaultShowtimes() {
        return """
            showtime-001|movie-001|theater-001|2025-03-15|10:00|12.00
            showtime-002|movie-001|theater-002|2025-03-15|14:00|18.00
            showtime-003|movie-002|theater-001|2025-03-15|17:00|12.00
            showtime-004|movie-002|theater-003|2025-03-15|20:00|15.00
            showtime-005|movie-003|theater-002|2025-03-16|11:00|18.00
            showtime-006|movie-003|theater-004|2025-03-16|19:00|22.00
            showtime-007|movie-004|theater-002|2025-03-16|14:00|18.00
            showtime-008|movie-005|theater-001|2025-03-16|21:00|12.00
            showtime-009|movie-001|theater-003|2025-03-17|15:00|15.00
            showtime-010|movie-002|theater-002|2025-03-17|18:00|18.00
            """;
    }
}