package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.dto.UserDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.exception.InvalidConfirmationToken;
import com.vodafone.ecommerce.mapper.UserEntityMapper;
import com.vodafone.ecommerce.model.ConfirmationToken;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.ConfirmationTokenRepository;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(RegistrationDto registrationDto) throws MessagingException {

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new DataIntegrityViolationException("Email Already Exists");
        }

        if (userRepository.existsByusername(registrationDto.getUsername())) {
            throw new DataIntegrityViolationException("username Already Exists");
        }

        UserEntity user = UserEntity.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .status(Status.UNVERIFIED)
                .loginFailureCount(0)
                .build();

        UserEntity result = userRepository.save(user);

        sendVerificationEmail(user);

        return UserEntityMapper.mapToUserDto(result);
    }

    //Todo handle exception for down mail server
    private void sendVerificationEmail(UserEntity user) throws MessagingException {

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        String token = confirmationToken.getConfirmationToken();

        String link = "http://localhost:" + serverPort + "/confirm-account/" + token;
        String links = "<a href='" + link + "'>" + "Verify" + "</a>";

        MimeMessage message = mailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Activate Account!");
        message.setContent("To confirm your account, please click here : " + links, "text/html; charset=utf-8");
        mailSender.send(message);
    }

    @Override
    public void resetAccount(String email) throws MessagingException {
        if (!userRepository.existsByEmail(email)) {
            return;
        }
        UserEntity user = userRepository.findByEmailIgnoreCase(email);
        sendVerificationEmail(user);
    }

    @Override
    public void confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token == null) {
            throw new InvalidConfirmationToken("Invalid Confirmation Token");
        }

        UserEntity user = token.getUser();
        user.setStatus(Status.ACTIVE);
        user.setLoginFailureCount(0);
        userRepository.save(user);
        confirmationTokenRepository.deleteById(token.getTokenId());
    }
}
