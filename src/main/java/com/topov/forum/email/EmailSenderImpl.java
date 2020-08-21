package com.topov.forum.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EmailSenderImpl implements EmailSender {
    @Override
    public void sendEmail(Email email) {
        log.debug("Sending email {}", email);
    }
}
