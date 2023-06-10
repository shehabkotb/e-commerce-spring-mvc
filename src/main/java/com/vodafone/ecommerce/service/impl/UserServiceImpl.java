package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.UserDto;
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
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final JavaMailSender mailSender;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDto saveUser(UserEntity user) throws MessagingException {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Email Already Exists");
        }

        user.setStatus(Status.UNVERIFIED);
        UserEntity result = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        String token = confirmationToken.getConfirmationToken();

        sendVerificationEmail(user, token);

        return UserEntityMapper.mapToUserDto(result);
    }

    private void sendVerificationEmail(UserEntity user, String token) throws MessagingException {

        String link = "http://localhost:" + serverPort + "/confirm-account/" + token;
        String links = "<a href='" + link + "'>" + "Verify" + "</a>";

        MimeMessage message = mailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Complete Registration!");
        message.setContent("To confirm your account, please click here : " + links, "text/html; charset=utf-8");
        mailSender.send(message);
    }

    @Override
    public void confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token == null) {
            throw new InvalidConfirmationToken();
        }

        UserEntity user = token.getUser();
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }
}
