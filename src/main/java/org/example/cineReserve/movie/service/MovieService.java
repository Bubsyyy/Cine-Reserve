package org.example.cineReserve.movie.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cineReserve.exception.DomainException;
import org.example.cineReserve.movie.model.Movie;
import org.example.cineReserve.movie.repository.MovieRepository;
import org.example.cineReserve.user.model.User;
import org.example.cineReserve.user.model.UserRole;
import org.example.cineReserve.web.dto.MovieAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Transactional
    public void addMovie(User user, MovieAddRequest movieAddRequest) {

        if (!isUserAdmin(user)){

            throw new AccessDeniedException("Access denied: User does not have admin privileges.");

        }

        Optional<Movie> optionalMovie = movieRepository.findByTitle(movieAddRequest.getTitle());

        if(optionalMovie.isPresent()){

            throw new DomainException("Movie already exists.");

        }

        Movie movie = Movie.builder()
                .title(movieAddRequest.getTitle())
                .description(movieAddRequest.getDescription())
                .poster(movieAddRequest.getPoster())
                .durationInMinutes(movieAddRequest.getDurationInMinutes())
                .build();



        movieRepository.save(movie);


        log.info("Successfully added new movie [%s]".formatted(movie.getTitle()));


    }

    private boolean isUserAdmin(User user) {

        return user.getRole() == UserRole.ADMIN;

    }
}
