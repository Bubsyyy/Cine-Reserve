package org.example.cineReserve.showtime.service;

import lombok.extern.slf4j.Slf4j;
import org.example.cineReserve.exception.DomainException;
import org.example.cineReserve.movie.model.Movie;
import org.example.cineReserve.movie.service.MovieService;
import org.example.cineReserve.screen.model.Screen;
import org.example.cineReserve.screen.service.ScreenService;
import org.example.cineReserve.seat.service.SeatService;
import org.example.cineReserve.showtime.model.Showtime;
import org.example.cineReserve.showtime.repository.ShowtimeRepository;
import org.example.cineReserve.user.model.User;
import org.example.cineReserve.user.model.UserRole;
import org.example.cineReserve.user.service.UserService;
import org.example.cineReserve.web.dto.ShowtimeAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ShowtimeService {



    private final ShowtimeRepository showtimeRepository;
    private final ScreenService screenService;
    private final MovieService movieService;
    private final SeatService seatService;
    private final UserService userService;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository, ScreenService screenService, MovieService movieService, SeatService seatService, UserService userService) {
        this.showtimeRepository = showtimeRepository;
        this.screenService = screenService;
        this.movieService = movieService;
        this.seatService = seatService;
        this.userService = userService;
    }


    @Transactional
    public void addShowtime(UUID userId, ShowtimeAddRequest showtimeAddRequest){

        User user = userService.getUserById(userId);

        if (!isUserAdmin(user)){

            throw new AccessDeniedException("Access denied: User does not have admin privileges.");

        }

        if(isShowtimeBeingProjected(showtimeAddRequest)){
            throw new DomainException("There is already a showtime in this screen for this time.");
        }

        Movie movieForShowtime = movieService.getMovieByTitle(showtimeAddRequest.getMovieTitle());
        Screen screenForShowtime = screenService.getScreenByName(showtimeAddRequest.getScreenName());
        LocalDateTime startTime = showtimeAddRequest.getStartTime();
        LocalDateTime endTime = calculateShowtimeEnd(showtimeAddRequest);


        Showtime showtime = Showtime.builder()
                .movie(movieForShowtime)
                .startTime(startTime)
                .endTime(endTime)
                .screen(screenForShowtime)
                .build();

        showtimeRepository.save(showtime);

        seatService.createNewSeats(showtime);

    }

    private boolean isUserAdmin(User user) {

        return user.getRole() == UserRole.ADMIN;

    }

    private boolean isShowtimeBeingProjected(ShowtimeAddRequest showtimeAddRequest) {

        Screen screen = screenService.getScreenByName(showtimeAddRequest.getScreenName());

        LocalDateTime startOfTheShowtime = showtimeAddRequest.getStartTime();
        LocalDateTime endOfTheShowtime = calculateShowtimeEnd(showtimeAddRequest);

        List<Showtime> showtimesDuringThisPeriod = showtimeRepository.findByScreenIdAndShowtimeRange(screen.getId(), startOfTheShowtime, endOfTheShowtime);

        if(showtimesDuringThisPeriod.isEmpty()){

            return false;

        }else{

            return true;

        }


    }

    private LocalDateTime calculateShowtimeEnd(ShowtimeAddRequest showtimeAddRequest){
        Movie movieToProject = movieService.getMovieByTitle(showtimeAddRequest.getMovieTitle());
        LocalDateTime startTime = showtimeAddRequest.getStartTime();
        int movieHours = movieToProject.getDurationInMinutes() / 60;
        int movieMinutes = movieToProject.getDurationInMinutes() % 60;

        return startTime.plusHours(movieHours).plusMinutes(movieMinutes);
    }
}
