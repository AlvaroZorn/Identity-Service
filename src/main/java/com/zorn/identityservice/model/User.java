package com.zorn.identityservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"user\"") // user is a reserved keyword in PostgreSQL. It is allowed only as quoted identifier.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @NotBlank(message = "Username is required")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "enabled")
    private boolean enabled;
}