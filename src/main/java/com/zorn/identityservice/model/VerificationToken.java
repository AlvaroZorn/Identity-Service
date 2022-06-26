package com.zorn.identityservice.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int tokenId;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiryDate", nullable = false)
    private Instant expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}