package com.topov.forum.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailSenderImplTest {
    private final EmailSender emailSender;

    @Autowired
    MailSenderImplTest(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Test
    public void sendEmail() {
        final var email = new Mail("Registration",  "boxeci5626@trufilth.com", "Hello");
        emailSender.sendEmail(email);
    }
}
