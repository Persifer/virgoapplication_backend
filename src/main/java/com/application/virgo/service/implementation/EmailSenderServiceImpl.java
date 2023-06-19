package com.application.virgo.service.implementation;

import com.application.virgo.DTO.outputDTO.ListUnviewMessageDTO;
import com.application.virgo.service.interfaces.EmailSenderService;
import com.application.virgo.service.interfaces.OffertaUtenteService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@EnableAsync
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender emailSender;
    private final OffertaUtenteService offertaUtenteService;


    @Override
    @Async
    public void sendWelcomeMail(String destinatario, String nome, String cognome) {


        SimpleMailMessage emailHandler = new SimpleMailMessage();

        emailHandler.setFrom(destinatario);

        emailHandler.setTo("giorgino.antonio32@gmail.com");
        emailHandler.setSubject("Virgo Platform - Welcome mail");

        emailHandler.setText(
                "Benvenuto nella famiglia di Virgo " + nome + " " + cognome + "!\n\n" +
                "Con la presente mail la vogliamo ringranziare per aver deciso di utilizzare la nostra piattaforma. \n"
        );

        emailSender.send(emailHandler);


    }

    @Override
    @Async
    @Scheduled(cron = "0 0 12 * * ?") // ogni giorno a mezzo giorno controlla quanti sono le persone con messaggi non visualizzati e gli invia una mail
    public void sendReminderToView() {
        List<ListUnviewMessageDTO> listUnviewMessage = offertaUtenteService.getListUnviewedMessaged();

        SimpleMailMessage emailHandler = new SimpleMailMessage();

        emailHandler.setFrom("virgoplatform.info@gmail.com");


        emailHandler.setSubject("Virgo Platform - Daily reminder");

        for(ListUnviewMessageDTO elem : listUnviewMessage){
            emailHandler.setTo(elem.getEmail());

            StringBuilder text = new StringBuilder("Salve! \n La piattaforma Virgo ti ricorda che hai ");

            if(elem.getUnviewedEmail() == 1){
                text.append(elem.getUnviewedEmail()).append(" sola offerta da visionare!");
            }else{
                text.append(elem.getUnviewedEmail()).append(" offerte da visionare!");
            }

            emailHandler.setText(text.toString());

            emailSender.send(emailHandler);

        }


    }
}
