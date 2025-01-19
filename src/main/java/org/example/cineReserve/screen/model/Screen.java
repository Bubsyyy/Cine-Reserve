package org.example.cineReserve.screen.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cineReserve.showtime.model.Showtime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private int capacity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "screen")
    private List<Showtime> showtimes = new ArrayList<>();
}
