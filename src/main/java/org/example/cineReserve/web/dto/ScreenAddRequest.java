package org.example.cineReserve.web.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ScreenAddRequest {

    private String name;

    @Positive(message = "The capacity must be positive number")
    private int capacity;


}
