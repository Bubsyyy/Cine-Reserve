package org.example.cineReserve.showtime.repository;

import org.example.cineReserve.showtime.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, UUID> {



    @Query(
            value = "SELECT * FROM showtimes " +
                    "WHERE screen_id = :id " +
                    "AND ((:showtimeStart BETWEEN start_time AND end_time) " +
                    "OR (:showtimeEnd BETWEEN start_time AND end_time))",
            nativeQuery = true
    )
    List<Showtime> findByScreenIdAndShowtimeRange(
            @Param("id") UUID id,
            @Param("showtimeStart") LocalDateTime showtimeStart,
            @Param("showtimeEnd") LocalDateTime showtimeEnd
    );



}
