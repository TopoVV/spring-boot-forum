package com.topov.forum.mail;

import com.topov.forum.dto.request.registration.RegistrationRequest;

public interface MailSender {
    void sendAccountConfirmationMail(RegistrationRequest registrationRequest, String accountConfirmationToken);
}
