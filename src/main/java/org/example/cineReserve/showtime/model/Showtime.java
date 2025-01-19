package org.example.cineReserve.showtime.model;

import jakarta.persistence.*;
import org.example.cineReserve.movie.model.Movie;
import org.example.cineReserve.reservation.model.Reservation;
import org.example.cineReserve.screen.model.Screen;
import org.example.cineReserve.seat.model.Seat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "showtimes")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "showtime")
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "showtime")
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;
}

