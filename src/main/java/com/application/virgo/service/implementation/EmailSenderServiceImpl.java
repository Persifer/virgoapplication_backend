package com.application.virgo.service.implementation;

import com.application.virgo.service.interfaces.EmailSenderService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;


    @Override
    public void sendWelcomeMail(String destinatario, String nome, String cognome) throws MessagingException {
        /*this.emailHandler.setFrom(this.from);

        this.emailHandler.setTo(destinatario);
        this.emailHandler.setSubject("Virgo Platform - Welcome mail");

        this.emailHandler.setText(
                "Benvenuto nella famiglia di Virgo " + nome + " " + cognome + "!\n\n" +
                "Con la presente mail la vogliamo ringranziare per aver deciso di utilizzare la nostra piattaforma. \n"
        );

        emailSender.send(emailHandler); */
        SimpleMailMessage emailHandler;
        MimeMessage message = this.emailSender.createMimeMessage();

        // Set From: header field of the header.
        String from = "virgoplatform.info@gmail.com";
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        //message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("giorgino.antonio32@gmail.com"));

        // Set Subject: header field
        message.setSubject("This is the Subject Line!");

        // Now set the actual message
        message.setText(
                "Benvenuto nella famiglia di Virgo " + nome + " " + cognome + "!\n\n" +
                    "Con la presente mail la vogliamo ringranziare per aver deciso di utilizzare la nostra piattaforma. \n"+
                    "Visita la pagina <a href='http://localhost:8080/site/immobile/list/0/20'> Home </a> del sito"
                ,"UTF-8", "html");

        emailSender.send(message);

    }
}
