package com.topov.forum.service;

import com.topov.forum.email.Email;

public interface EmailSender {
    sendEmail(Email email);
}
