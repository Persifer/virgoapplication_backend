package com.application.virgo.exec;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.application.virgo.controller",
										   "com.application.virgo.service",
		"com.application.virgo.repositories","com.application.virgo.DTO.Mapper",
		"com.application.virgo.DTO", "com.application.virgo.wrapperclass", "com.application.virgo.configuration"
})
@EnableJpaRepositories(basePackages = "com.application.virgo.repositories")
@EntityScan(basePackages = "com.application.virgo.model")
public class VirgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirgoApplication.class, args);
	}

}

	/*
	* TODO
	*  2. Implementazione "chat" per le offerte/contratti
	* 	  2.2 Creazione sezione chat utente con la lista di tutte le offerte
	*  	  2.3. Creazione zona utente in cui poter visionare tutti i contratti
	*  4. Invio email per messaggi non letti
	*  5. Inserisci i commenti a supporto della relazione tra utente e ruolo
	*  6. Sorting dei risultati di immobile
	*     7.1 parole chiave
	* 	  7.2 parametri specifici di filtraggio
	*  7. Caricamento delle immagini nella sezione creazione e nella sezione modifica
	*  8. Visualizzazione delle immagini nella pagina di un immobile
	*  9. Test
	*
	*
	* */


