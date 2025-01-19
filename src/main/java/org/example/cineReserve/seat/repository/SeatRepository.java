package org.example.cineReserve.seat.repository;

import org.example.cineReserve.seat.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
}
