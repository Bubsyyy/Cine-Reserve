package org.example.cineReserve.movie.repository;

import jakarta.validation.constraints.Size;
import org.example.cineReserve.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    Optional<Movie> findByTitle(String title);
}
