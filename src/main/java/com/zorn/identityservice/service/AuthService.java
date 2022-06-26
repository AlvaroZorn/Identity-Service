package com.zorn.identityservice.service;

import com.zorn.identityservice.dto.UserDto;
import com.zorn.identityservice.model.User;
import com.zorn.identityservice.model.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Slf4j
@Service
public class AuthService {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final MailService mailService;

    @Value("${verification.path}")
    private String verificationPath;

    public AuthService(UserService userService, VerificationTokenService verificationTokenService, MailService mailService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.mailService = mailService;
    }

    public User register(UserDto userDto) {

        User user = userService.saveUser(userDto);

        VerificationToken verificationToken = verificationTokenService.saveToken(user);

        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("verificationLink", "http://localhost:8080" + verificationPath + "/" + verificationToken.getToken());

        mailService.sendMail(context, "mail-templates/registration", user.getEmail(), "Please Activate your Account");

        return user;
    }

}
