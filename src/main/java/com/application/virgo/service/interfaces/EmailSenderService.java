package com.application.virgo.service.interfaces;

public interface EmailSenderService {

    /**
     * Invia una mail di benvenuto al destinatario
     * @param destinatario email del destinatario
     * @param nome nome del destinatario
     * @param cognome cognome del destinatatario
     */
    public void sendWelcomeMail(String destinatario, String nome, String cognome);

    public void sendReminderToView();
}
