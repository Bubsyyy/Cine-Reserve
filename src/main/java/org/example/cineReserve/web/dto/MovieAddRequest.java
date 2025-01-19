package org.example.cineReserve.web.dto;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class MovieAddRequest {

    @Size(min = 2, message = "Username must be at least 2 symbols")
    private String title;

    @Size(min = 15, message = "Username must be at least 15 symbols")
    private String description;

    private String poster;

    @Positive(message = "The movie duration time must be positive number")
    private int durationInMinutes;


}
