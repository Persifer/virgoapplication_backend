package exec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.azienda.progettoFinale.model")
public class VirgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirgoApplication.class, args);
	}

}
