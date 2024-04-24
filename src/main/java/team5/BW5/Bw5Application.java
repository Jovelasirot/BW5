package team5.BW5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import team5.BW5.entities.Address;
import team5.BW5.entities.Client;
import team5.BW5.entities.Town;

@SpringBootApplication
public class Bw5Application {

	public static void main(String[] args) {
		SpringApplication.run(Bw5Application.class, args);
		Town town = new Town();
		Client client = new Client();
		Address address = new Address("via roma", "50", "345623", "location", town, client );
	}

}
