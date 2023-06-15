package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = EmailService.class)
 class EmailServiceTest {
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void sendEmailTest_sendEmail_returnNothing(){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("hema@example.com");
        email.setTo("ali@example.com");
        email.setSubject("Test Subject");
        email.setText("Test Body");

        emailService.sendEmail(email);

        verify(javaMailSender, times(1)).send(email);

    }

}
