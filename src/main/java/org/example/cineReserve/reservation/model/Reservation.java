package org.example.cineReserve.reservation.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cineReserve.seat.model.Seat;
import org.example.cineReserve.showtime.model.Showtime;
import org.example.cineReserve.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    private User owner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation")
    private List<Seat> reservedSeats = new ArrayList<>();

    @ManyToOne
    private Showtime showtime;

}

