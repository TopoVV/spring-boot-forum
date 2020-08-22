package com.topov.forum.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailSenderIT {
    private final MailSender mailSender;

    @Autowired
    MailSenderIT(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Test
    public void sendEmail() {
        final var email = new Mail("Registration",  "boxeci5626@trufilth.com", "Hello");
        Assertions.assertDoesNotThrow(() -> mailSender.sendMail(email));
    }
}
