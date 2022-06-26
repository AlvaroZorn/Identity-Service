package com.zorn.identityservice.service;

import com.zorn.identityservice.dto.UserDto;
import com.zorn.identityservice.model.Email;
import com.zorn.identityservice.model.User;
import com.zorn.identityservice.model.VerificationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final MailService mailService;

    public AuthService(UserService userService, VerificationTokenService verificationTokenService, MailService mailService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.mailService = mailService;
    }

    public void register(UserDto userDto) {

        User user = userService.saveUser(userDto);
        VerificationToken verificationToken = verificationTokenService.saveToken(user);

        Email email = Email.builder()
                .subject("Please Activate your Account")
                .text("Thank you for signing up to Spring Reddit, please click on the below url to activate your account: http://localhost:8080/api/auth/accountVerification/" + verificationToken.getToken())
                .to(user.getEmail())
                .build();

        mailService.sendMail(email);
    }

}
