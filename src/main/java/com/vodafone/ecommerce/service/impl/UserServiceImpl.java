package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.UserEntityDto;
import com.vodafone.ecommerce.enums.Role;
import com.vodafone.ecommerce.enums.Status;
import com.vodafone.ecommerce.mapper.UserMapper;
import com.vodafone.ecommerce.model.ConfirmationToken;
import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.repository.ConfirmationTokenRepository;
import com.vodafone.ecommerce.repository.UserRepository;
import com.vodafone.ecommerce.service.EmailService;
import com.vodafone.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;


    private final JavaMailSender mailSender ;
    @Override
    public ResponseEntity<?> saveUser(UserEntity user) throws MessagingException {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);
        String token=confirmationToken.getConfirmationToken();
        String link="http://localhost:8085/confirm-account?token="+token;


        String links="<a href='" + link + "'>" + "Verify" + "</a>";



        MimeMessage message = mailSender.createMimeMessage();
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Complete Registration!");

        message.setContent("To confirm your account, please click here : "   +links,"text/html; charset=utf-8");

        mailSender.send(message);
        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            UserEntity user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    @Override
    public UserEntity saveAdmin(UserEntityDto userEntityDto) {
        UserEntity user = UserMapper.mapToUser(userEntityDto);
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllAdmin() {

        return userRepository.findByRole(Role.ADMIN);
    }
}
