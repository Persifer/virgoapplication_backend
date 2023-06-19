package com.application.virgo.service.interfaces;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;


public interface EmailSenderService {

    /**
     * Invia una mail di benvenuto al destinatario
     * @param destinatario email del destinatario
     * @param nome nome del destinatario
     * @param cognome cognome del destinatatario
     * @throws MessagingException se non riesce ad inviare la mail
     */
    public void sendWelcomeMail(String destinatario, String nome, String cognome) throws MessagingException;

    /**
     * Permette di inviare una mail che ricorda quanti messaggi deve visualizzare un utente
     */
    public void sendReminderToView();
}
