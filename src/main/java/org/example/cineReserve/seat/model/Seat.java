package org.example.cineReserve.seat.model;

import jakarta.persistence.*;
import org.example.cineReserve.reservation.model.Reservation;
import org.example.cineReserve.showtime.model.Showtime;

import java.util.UUID;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean isAvailable;

    private int seatNumber;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private Showtime showtime;
}
