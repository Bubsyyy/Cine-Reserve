package org.example.cineReserve.screen.service;


import lombok.extern.slf4j.Slf4j;
import org.example.cineReserve.exception.DomainException;
import org.example.cineReserve.movie.model.Movie;
import org.example.cineReserve.screen.model.Screen;
import org.example.cineReserve.screen.repository.ScreenRepository;
import org.example.cineReserve.user.model.User;
import org.example.cineReserve.user.model.UserRole;
import org.example.cineReserve.web.dto.ScreenAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ScreenService {

    private final ScreenRepository screenRepository;

    @Autowired
    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }


    public void addScreen(User user, ScreenAddRequest screenAddRequest) {

        if (!isUserAdmin(user)){

            throw new AccessDeniedException("Access denied: User does not have admin privileges.");

        }

        Optional<Screen> optionalScreen = screenRepository.findByName(screenAddRequest.getName());

        if (optionalScreen.isPresent()) {

            throw new DomainException("Screen with that name already exists.");

        }

        Screen screen = Screen.builder()
                .name(screenAddRequest.getName())
                .capacity(screenAddRequest.getCapacity())
                .build();

        screenRepository.save(screen);

        log.info("Successfully added screen [%s]".formatted(screen.getName()));



    }



    private boolean isUserAdmin(User user) {

        return user.getRole() == UserRole.ADMIN;

    }

    public Screen getScreenByName(String screenName) {

        Optional<Screen> optionalScreen = screenRepository.findByName(screenName);
        if (optionalScreen.isPresent()) {
            return optionalScreen.get();
        }

        throw new DomainException("Screen with that name does not exist.");
    }
}
