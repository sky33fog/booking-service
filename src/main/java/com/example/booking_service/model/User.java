package com.example.booking_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {

    @Id
    private String name;

    private String password;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
    private List<RoleType> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    public List<SimpleGrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).toList();
    }
}
