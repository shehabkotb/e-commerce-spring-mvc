package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);
    List<UserEntity> findByRole(Role role);




}
