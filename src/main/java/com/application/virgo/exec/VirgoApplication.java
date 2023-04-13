package com.application.virgo.exec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.application.virgo.model")
public class VirgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirgoApplication.class, args);
	}

	/*
	* TODO
	*  1. Implementa controller
	*  2. Implementa service
	*  3. Implementa security
	*  4. Capisci se serve avere la compoundKey per la relazione tra utente e ruolo
	*  5. Inserisci i commenti a supporto della relazione tra utente e ruolo
	*  6. Implementa l'entità contratto all'interno del database
	* */

}
