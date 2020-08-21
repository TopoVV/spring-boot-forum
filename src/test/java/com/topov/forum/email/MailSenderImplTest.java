package com.topov.forum.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailSenderImplTest {
    private final MailSender mailSender;

    @Autowired
    MailSenderImplTest(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Test
    public void sendEmail() {
        final var email = new Mail("Registration",  "boxeci5626@trufilth.com", "Hello");
        mailSender.sendMail(email);
    }
}
