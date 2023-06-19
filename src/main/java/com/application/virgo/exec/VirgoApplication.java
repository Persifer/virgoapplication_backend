package com.application.virgo.exec;


import com.application.virgo.service.interfaces.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.application.virgo.controller",
										   "com.application.virgo.service",
		"com.application.virgo.repositories","com.application.virgo.DTO.Mapper",
		"com.application.virgo.DTO", "com.application.virgo.wrapperclass", "com.application.virgo.configuration"
})
@EnableJpaRepositories(basePackages = "com.application.virgo.repositories")
@EntityScan(basePackages = "com.application.virgo.model")

public class VirgoApplication {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(VirgoApplication.class, args);
	}


}

	/*
	* TODO
	*  6. Sorting dei risultati di immobile
	*     7.1 parole chiave
	* 	  7.2 parametri specifici di filtraggio
	*  9. Test
	*  12. Implementa modifica utente
	*  16. Creazione metodo eliminazione immobile --> si può fare front-end spedendo i dati con isEnabled a false
	* */


