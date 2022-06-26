package com.zorn.identityservice.service;

import com.zorn.identityservice.dto.UserDto;
import com.zorn.identityservice.model.User;
import com.zorn.identityservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User saveUser(UserDto userDto) {

        checkIfUsernameAlreadyTaken(userDto);
        checkIfEmailAlreadyExists(userDto);

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);
        log.info("User was saved successfully");

        return user;
    }

    private void checkIfUsernameAlreadyTaken(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());

        user.ifPresent(value -> {
            throw new IllegalArgumentException("Username " + value.getUsername() + " already taken!");
        });
    }

    private void checkIfEmailAlreadyExists(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        user.ifPresent(value -> {
            throw new IllegalArgumentException("Email " + value.getEmail() + " already exists!");
        });
    }
}
