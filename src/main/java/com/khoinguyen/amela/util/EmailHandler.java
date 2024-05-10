package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailHandler {
    private static final Logger log = LoggerFactory.getLogger(EmailHandler.class);
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
    public void emailMessage(String subject, String senderName, String mailContent,
                             JavaMailSender mailSender, User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(email, senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    // send multiple mail
    @Async
    public void sendNotificationMessage(MessageSchedule messageSchedule, List<User> users) throws MessagingException, UnsupportedEncodingException {
        String subject = "Notification New Messages";
        String senderName = "Users Notification Service";
        String url = Constant.HOST + "messages/detail/" + messageSchedule.getId();
        String mailContent = TemplateEmailGenerate.getHtmlNotificationMessages(url);
        String[] emails = users.stream().map(User::getEmail).toArray(String[]::new);
        listEmailMessage(subject, senderName, mailContent, javaMailSender, emails);
    }

    @Async
    public void listEmailMessage(String subject, String senderName, String mailContent,
                                 JavaMailSender mailSender, String[] emails) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(email, senderName);
        messageHelper.setTo(emails);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
