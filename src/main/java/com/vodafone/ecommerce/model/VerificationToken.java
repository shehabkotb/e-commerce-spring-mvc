package com.vodafone.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="confirmationToken")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private Instant expiryDate=Instant.now().plus(Duration.ofHours(24));

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    public boolean isExpired(){
        return Instant.now().isAfter(expiryDate);
    }


    public VerificationToken(UserEntity user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }



}
