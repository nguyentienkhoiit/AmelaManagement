package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailHandler {
    final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String email;

    @Async
    public void sendTokenForgotPassword(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Users Verification Service";
        String receiverName = user.getFirstname() + " " + user.getLastname();
        String mailContent = TemplateEmailGenerate.getHtmlForgotPassword(receiverName, url);
        emailMessage(subject, senderName, mailContent, javaMailSender, user);
    }

    @Async
    public void sendTokenCreateUser(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "New Password Request Verification";
        String senderName = "Users Verification Service";
        String receiverName = user.getFirstname() + " " + user.getLastname();
        String mailContent = TemplateEmailGenerate.getHtmlNewPassword(receiverName, url);
        emailMessage(subject, senderName, mailContent, javaMailSender, user);
    }

    private void emailMessage(String subject, String senderName, String mailContent,
                              JavaMailSender mailSender, User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(email, senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
