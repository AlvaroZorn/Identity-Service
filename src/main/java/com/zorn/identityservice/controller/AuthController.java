package com.zorn.identityservice.controller;

import com.zorn.identityservice.dto.UserDto;
import com.zorn.identityservice.model.User;
import com.zorn.identityservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        User user = authService.register(userDto);
        return ResponseEntity.ok("User Registration for the user with the email address " + user.getEmail() + " successfully!");
    }
}
