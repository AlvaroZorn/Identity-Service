package com.zorn.identityservice.service;

import com.zorn.identityservice.model.User;
import com.zorn.identityservice.model.VerificationToken;
import com.zorn.identityservice.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${verificationToken.expiryTime}")
    private int expiryTime;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken saveToken(User user) {

        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plusMillis(expiryTime))
                .build();

        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }
}
