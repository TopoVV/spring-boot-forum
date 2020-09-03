package com.topov.forum.mail;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Log4j2
@Service
public class MailSenderImpl implements MailSender {
    private static final String ACCOUNT_CONFIRMATION_MAIL_TEMPLATE = "Welcome to Forum, %s. " +
        "To confirm your account follow this link: %s";
    private static final String MAIL_SENDER = "vladtopov2001@gmail.com";
    private final JavaMailSender mailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAccountConfirmationMail(RegistrationRequest registrationRequest, String accountConfirmationToken) {
        log.debug("Sending a mail to {}", registrationRequest.getEmail());
        try {

            final String content = generateConfirmationMailContent(registrationRequest, accountConfirmationToken);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_SENDER);
            message.setSubject("Registration");
            message.setTo(registrationRequest.getEmail());
            message.setText(content);
            mailSender.send(message);

        } catch (MailException e) {
            log.error("Error while sending the mail", e);
            throw e;
        }
    }

    private String generateConfirmationMailContent(RegistrationRequest registrationRequest, String token) {
        final String username = registrationRequest.getUsername();
        final String confirmationUrl = createRegistrationConfirmationUrl(token);
        return String.format(ACCOUNT_CONFIRMATION_MAIL_TEMPLATE, username, confirmationUrl);
    }

    private String createRegistrationConfirmationUrl(String token) {
        final String tokenConfirmationPath = String.format("registration/%s", token);

        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port("8080")
            .path(tokenConfirmationPath)
            .build()
            .toString();
    }
}
