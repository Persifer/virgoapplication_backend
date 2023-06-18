package com.application.virgo.service.implementation;

import com.application.virgo.service.interfaces.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;


    @Override
    public void sendWelcomeMail(String destinatario, String nome, String cognome) throws MessagingException {


        SimpleMailMessage emailHandler = new SimpleMailMessage();

        emailHandler.setFrom("virgoplatform.info@gmail.com");

        emailHandler.setTo("giorgino.antonio32@gmail.com");
        emailHandler.setSubject("Virgo Platform - Welcome mail");

        emailHandler.setText(
                "Benvenuto nella famiglia di Virgo " + nome + " " + cognome + "!\n\n" +
                "Con la presente mail la vogliamo ringranziare per aver deciso di utilizzare la nostra piattaforma. \n"
        );

        emailSender.send(emailHandler);



    }
}
