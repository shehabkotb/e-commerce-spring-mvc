package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.exception.InvalidConfirmationToken;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.model.VerificationToken;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.repository.VerificationTokenRepository;
import com.vodafone.ecommerce.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserService.class)
 class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MimeMessage mimeMessage;
    @Mock
    private JavaMailSender mailSender;

    @Mock
    VerificationTokenRepository verificationTokenRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void saveUserTest_saveUser_returnUser() throws MessagingException {
        //Arrange
        RegistrationDto registrationDto= RegistrationDto.builder()
                .username("hema")
                .email("hema@example.com")
                .password("pass")
                .build();
        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("hema")
                .email("hema@example.com")
                .password("pass")
                .role(Role.USER)
                .build();

        VerificationToken verificationToken = new VerificationToken(user);
        verificationToken.setConfirmationToken("testtoken");


        //Act
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByusername(user.getUsername())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(passwordEncoder.encode("pass")).thenReturn("encodedpassword");
        when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        UserDto userDto = userService.registerUser(registrationDto);

        //Assert
        assertNotNull(userDto);

    }
    @Test
     void conformationEmail_ConfirmEmail_ReturnValidToken() {
        // Arrange
        String confirmationToken = "valid_token";
        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("hema")
                .email("hema@example.com")
                .password("pass")
                .role(Role.USER)
                .status(Status.SUSPENDED)
                .loginFailureCount(3)
                .build();

        VerificationToken token = VerificationToken.builder()
                .tokenId(1L)
                .user(user)
                .confirmationToken(confirmationToken)
                .createdDate(new Date())
                .build();

        when(verificationTokenRepository.findByConfirmationToken(confirmationToken)).thenReturn(token);

        // Act
        userService.confirmEmail(confirmationToken);

        // Assert
        verify(verificationTokenRepository).findByConfirmationToken(confirmationToken);
        verify(userRepository).save(user);
        verify(verificationTokenRepository).deleteById(token.getTokenId());
        assertEquals(Status.ACTIVE, user.getStatus());
        assertEquals(0, user.getLoginFailureCount());
    }
    @Test
     void confirmEmailTest_ConfirmEmail_ReturnInvalidToken() {
        // Arrange
        String confirmationToken = "invalid_token";
        when(verificationTokenRepository.findByConfirmationToken(confirmationToken)).thenReturn(null);

        // Assert
        assertThrows(InvalidConfirmationToken.class,()->userService.confirmEmail(confirmationToken));
    }





    }

