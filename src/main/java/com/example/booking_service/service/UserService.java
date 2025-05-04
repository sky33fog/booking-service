package com.example.booking_service.service;

import com.example.booking_service.exception.UserAlreadyExistException;
import com.example.booking_service.exception.EntityNotFoundException;
import com.example.booking_service.model.RoleType;
import com.example.booking_service.model.User;
import com.example.booking_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User findByName(String name) {
        return userRepository.findById(name).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Entity with name: {0}, not found!", name)));
    }

    public User save(User user, RoleType roleType) {
        if(userRepository.existsByName(user.getName())) {
            throw new UserAlreadyExistException(MessageFormat
                    .format("User with name {0} already exists.", user.getName()));
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException(MessageFormat
                    .format("User with email {0} already exists.", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleType);
        return userRepository.save(user);
    }
}
