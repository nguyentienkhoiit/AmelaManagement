package com.khoinguyen.amela.util;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.khoinguyen.amela.entity.User;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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

    @Async
    public void emailMessage(
            String subject, String senderName, String mailContent, JavaMailSender mailSender, User user)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(email, senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    //    send multiple mail
    //    @Async
    //    public void sendNotificationMessage(MessageSchedule messageSchedule, List<User> users) throws
    // MessagingException, UnsupportedEncodingException {
    //        String subject = "Notification New Messages";
    //        String senderName = "Users Notification Service";
    //        String url = appConfig.HOST + "messages/detail/" + messageSchedule.getId();
    //        String mailContent = TemplateEmailGenerate.getHtmlNotificationMessages(url);
    //        String[] emails = users.stream().map(User::getEmail).toArray(String[]::new);
    //        emailListMessage(subject, senderName, mailContent, javaMailSender, emails);
    //    }
    //
    //    @Async
    //    public void emailListMessage(String subject, String senderName, String mailContent,
    //                                 JavaMailSender mailSender, String[] emails) throws MessagingException,
    // UnsupportedEncodingException {
    //        MimeMessage message = mailSender.createMimeMessage();
    //        var messageHelper = new MimeMessageHelper(message);
    //        messageHelper.setFrom(email, senderName);
    //        messageHelper.setTo(emails);
    //        messageHelper.setSubject(subject);
    //        messageHelper.setText(mailContent, true);
    //        mailSender.send(message);
    //    }
}
