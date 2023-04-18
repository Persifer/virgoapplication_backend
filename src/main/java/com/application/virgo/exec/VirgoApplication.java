package com.application.virgo.exec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.application.virgo.service","com.application.virgo.controller",
		"com.application.virgo.configuration"})
@EnableJpaRepositories(basePackages = {"com.application.virgo.repositories"} )
@EntityScan( basePackages = {"com.application.virgo.model"} )
public class VirgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirgoApplication.class, args);
	}

	/*
	* TODO
	*  1. Implementa controller
	* 	1.1 Implementa paginazione controller immobile -> https://www.youtube.com/watch?v=klFTtvy0KII / 
	*  2. Implementa service
	* 	2.1 Implementa i metodi per gestire registrazione e login come si vede. Vedi Baeldung
	* 	2.2 Implementa nel modo corretto registrazione e login utente
	*  3. Implementa spring security
	* 	3.1 Inserisci l'interfaccia UserDetails all'interno della classe utente
	*  4. Capisci se serve avere la compoundKey per la relazione tra utente e ruolo
	*  5. Inserisci i commenti a supporto della relazione tra utente e ruolo
	*
	*
	* */

}
