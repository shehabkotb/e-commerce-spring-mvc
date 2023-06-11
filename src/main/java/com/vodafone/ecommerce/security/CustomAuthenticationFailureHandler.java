package com.vodafone.ecommerce.security;


import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    final int MAX_FAILED_ATTEMPTS = 3;
    private UserRepository userRepository;

    @Autowired
    public CustomAuthenticationFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            Integer updatedFailureCount = user.getLoginFailureCount() + 1;
            user.setLoginFailureCount(updatedFailureCount);

            if (updatedFailureCount >= MAX_FAILED_ATTEMPTS) {
                user.setStatus(Status.SUSPENDED);
                exception = new LockedException("Your account has been locked due to 3 failed attempts. Reset account with your email");
            } else {
                exception = new BadCredentialsException("Invalid Username or Password");
            }
            userRepository.save(user);
        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
