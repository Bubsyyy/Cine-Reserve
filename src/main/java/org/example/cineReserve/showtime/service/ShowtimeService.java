package org.example.cineReserve.showtime.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cineReserve.exception.DomainException;
import org.example.cineReserve.movie.model.Movie;
import org.example.cineReserve.movie.service.MovieService;
import org.example.cineReserve.screen.model.Screen;
import org.example.cineReserve.screen.service.ScreenService;
import org.example.cineReserve.showtime.model.Showtime;
import org.example.cineReserve.showtime.repository.ShowtimeRepository;
import org.example.cineReserve.user.model.User;
import org.example.cineReserve.user.model.UserRole;
import org.example.cineReserve.web.dto.ShowtimeAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShowtimeService {



    private final ShowtimeRepository showtimeRepository;
    private final ScreenService screenService;
    private final MovieService movieService;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository, ScreenService screenService, MovieService movieService) {
        this.showtimeRepository = showtimeRepository;
        this.screenService = screenService;
        this.movieService = movieService;
    }

    public void addShowtime(User user, ShowtimeAddRequest showtimeAddRequest){

        if (!isUserAdmin(user)){

            throw new AccessDeniedException("Access denied: User does not have admin privileges.");

        }

        if(isShowtimeBeingProjected(showtimeAddRequest)){
            throw new DomainException("There is already a showtime in this screen for this time.");
        }

        Movie movieForShowtime = movieService.getMovieByTitle(showtimeAddRequest.getMovieTitle());
        Screen screenForShowtime = screenService.getScreenByName(showtimeAddRequest.getScreenName());
        int movieHours = movieForShowtime.getDurationInMinutes() / 60;
        int movieMinutes = movieForShowtime.getDurationInMinutes() % 60;

        LocalDateTime endTime = showtimeAddRequest.getStartTime().plusHours(movieHours).plusMinutes(movieMinutes);

        //TODO save and initialize seats


    }

    private boolean isUserAdmin(User user) {

        return user.getRole() == UserRole.ADMIN;

    }

    private boolean isShowtimeBeingProjected(ShowtimeAddRequest showtimeAddRequest) {

        Screen screen = screenService.getScreenByName(showtimeAddRequest.getScreenName());

        LocalDateTime startOfTheShowtime = showtimeAddRequest.getStartTime();

        List<Showtime> showtimesDuringThisPeriod = showtimeRepository.findByScreenIdAndDateWithinTimeframe(screen.getId(), startOfTheShowtime);

        if(showtimesDuringThisPeriod.isEmpty()){

            return false;

        }else{

            return true;

        }


    }
}
