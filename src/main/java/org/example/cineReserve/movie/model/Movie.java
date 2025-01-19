package org.example.cineReserve.movie.model;

import jakarta.persistence.*;
import org.example.cineReserve.showtime.model.Showtime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private String poster;

    private int durationInMinutes;

    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
    private List<Showtime> showtimes = new ArrayList<>();

}
