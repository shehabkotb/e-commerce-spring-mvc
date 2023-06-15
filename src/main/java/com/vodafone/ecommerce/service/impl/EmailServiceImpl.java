package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
