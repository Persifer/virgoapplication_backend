package com.application.virgo.service.interfaces;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

public interface EmailSenderService {

    public void sendWelcomeMail(String destinatario, String nome, String cognome) throws MessagingException;
}
