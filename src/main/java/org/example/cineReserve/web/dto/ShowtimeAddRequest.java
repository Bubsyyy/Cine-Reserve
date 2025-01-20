package org.example.cineReserve.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowtimeAddRequest {

    private LocalDateTime startTime;

    private String movieTitle;

    private String screenName;


}
