package com.topov.forum.mail;

import com.topov.forum.dto.request.registration.RegistrationRequest;
import com.topov.forum.token.AccountConfirmationToken;

public interface MailSender {
    void sendAccountConfirmationMail(RegistrationRequest registrationRequest, String accountConfirmationToken);
}
