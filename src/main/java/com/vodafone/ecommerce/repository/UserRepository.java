package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);


}
