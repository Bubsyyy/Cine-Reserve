package org.example.cineReserve.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.cineReserve.reservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true )
    private String username;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Reservation> reservations = new ArrayList<>();




}