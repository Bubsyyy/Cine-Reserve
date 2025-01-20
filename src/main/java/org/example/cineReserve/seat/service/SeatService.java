package org.example.cineReserve.seat.service;


import org.example.cineReserve.seat.model.Seat;
import org.example.cineReserve.seat.repository.SeatRepository;
import org.example.cineReserve.showtime.model.Showtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void createNewSeats(Showtime showtime) {

        int capacity = showtime.getScreen().getCapacity();
        List<Seat> seats = new ArrayList<>();


        for (int seatNum = 1; seatNum <= capacity; seatNum++) {

            Seat seat = Seat.builder()
                    .seatNumber(seatNum)
                    .isAvailable(true)
                    .showtime(showtime)
                    .build();

            seats.add(seat);
        }

        seatRepository.saveAll(seats);

    }
}
