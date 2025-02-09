package org.example.cineReserve.web;

import org.example.cineReserve.movie.service.MovieService;
import org.example.cineReserve.screen.service.ScreenService;
import org.example.cineReserve.showtime.service.ShowtimeService;
import org.example.cineReserve.user.model.User;
import org.example.cineReserve.user.model.UserRole;
import org.example.cineReserve.user.repository.UserRepository;
import org.example.cineReserve.user.service.UserService;
import org.example.cineReserve.web.dto.MovieAddRequest;
import org.example.cineReserve.web.dto.ScreenAddRequest;
import org.example.cineReserve.web.dto.ShowtimeAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MovieService movieService;
    private final ScreenService screenService;
    private final ShowtimeService showtimeService;

    @Autowired
    public CommandLineRunnerImpl(UserRepository userRepository, MovieService movieService, ScreenService screenService,ShowtimeService showtimeService) {
        this.userRepository = userRepository;
        this.movieService = movieService;
        this.screenService = screenService;
        this.showtimeService = showtimeService;
    }

    @Override
    public void run(String... args) throws Exception {




        Optional<User> userOptional = this.userRepository.findByUsername("sasdsdfsdffsddsa");
        User user = userOptional.get();

        /*
        System.out.println(user.getUsername());
        System.out.println(user.getId());

        MovieAddRequest movieAddRequest = new MovieAddRequest();
        movieAddRequest.setDescription("sdfsdafdfsdfasfsdffdfasdfsdf");
        movieAddRequest.setTitle("Adam");
        movieAddRequest.setDurationInMinutes(130);
        movieAddRequest.setPoster("sdfsvasdfd");


        movieService.addMovie(user,movieAddRequest);


         */


        /*
        ScreenAddRequest screenAddRequest = new ScreenAddRequest();
        screenAddRequest.setCapacity(20);
        screenAddRequest.setName("Zalalala");

        screenService.addScreen(user.getId(), screenAddRequest);


         */

        ShowtimeAddRequest showtimeAddRequest = new ShowtimeAddRequest();
        showtimeAddRequest.setMovieTitle("Adam");
        showtimeAddRequest.setScreenName("Zalalala");
        showtimeAddRequest.setStartTime(LocalDateTime.now().plusDays(1));



        showtimeService.addShowtime(user.getId(),showtimeAddRequest);

    }
}
