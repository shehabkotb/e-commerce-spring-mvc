package com.vodafone.ecommerce.security;

import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {

            boolean nonLocked = !user.getStatus().equals(Status.SUSPENDED);
            boolean notVerified = user.getStatus().equals(Status.UNVERIFIED);

            if (notVerified) {
                throw new DisabledException("Email is not Verified");
            }

            UserDetails authUser = CustomUserDetails.CustomUserDetailsBuilder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                    .enabled(true)
                    .accountNonExpired(true)
                    .credentialsNonExpired(true)
                    .accountNonLocked(nonLocked)
                    .build();

            return authUser;
        } else {
            throw new UsernameNotFoundException("Bad credentials");
        }
    }
}
