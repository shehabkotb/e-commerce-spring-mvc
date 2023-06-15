package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByConfirmationToken(String confirmationToken);
    
}
