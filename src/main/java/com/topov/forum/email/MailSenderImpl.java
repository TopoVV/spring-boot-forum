package com.topov.forum.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MailSenderImpl implements MailSender {
    private static final String MAIL_SENDER = "vladtopov2001@gmail.com";
    private final JavaMailSender mailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(Mail mail) {
        log.debug("Sending a mail to {}", mail.getRecipient());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_SENDER);
        message.setTo(mail.getRecipient());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailSender.send(message);
    }
}
