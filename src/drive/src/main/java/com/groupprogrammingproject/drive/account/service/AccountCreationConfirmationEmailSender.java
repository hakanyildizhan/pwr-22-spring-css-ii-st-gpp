package com.groupprogrammingproject.drive.account.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountCreationConfirmationEmailSender {

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(String email, String activationId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(email);
        message.setSubject("Activate your account");
        message.setText("Thank you for creating the account. In order to activate it, please click the link: localhost:8080/accounts?action=activation&activationId=" + activationId);
        emailSender.send(message);
    }
}