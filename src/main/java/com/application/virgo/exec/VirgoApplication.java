package com.application.virgo.exec;

import com.application.virgo.model.Ruolo;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.RuoloJpaRepository;
import com.application.virgo.repositories.UtenteJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.application.virgo.controller",
										   "com.application.virgo.service",
		"com.application.virgo.repositories",
		"com.application.virgo.DTO", "com.application.virgo.wrapperclass", "com.application.virgo.configuration"
})
@EnableJpaRepositories(basePackages = "com.application.virgo.repositories")
@EntityScan(basePackages = "com.application.virgo.model")
public class VirgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirgoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(RuoloJpaRepository ruoloRepo ) {
//		return args -> {
//			ruoloRepo.save(new Ruolo("ROLE_USER"));
//			ruoloRepo.save(new Ruolo("ROLE_ADMIN"));
//		};
//	}

	/*
	*     public Utente(String nome, String cognome, String email, String password,
                  String via, String cap, String citta, String provincia, Date dataNascita)*/
}

	/*
	* TODO
	*  1. Implementa controller
	* 	1.1 Implementa paginazione controller immobile -> https://www.youtube.com/watch?v=klFTtvy0KII / 
	*  2. Implementa service
	* 	2.1 Implementa i metodi per gestire registrazione e login come si vede. Vedi Baeldung
	* 	2.2 Implementa nel modo corretto registrazione e login utente
	* 	2.3 SERVICE IMMOBILE
	* 		2.3.1 Implementa dentro createNewImmobile i metodi per un corretto inserimento dei dati
	*  3. Inserisci paginazione per prelevare gli immobili
	*  4. Inserisci i commenti a supporto della relazione tra utente e ruolo
	*  5. Capisci che tipo di DTO utilizzare per la registrazione di un utente
	*
	*
	* */


