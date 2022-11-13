package cz.cvut.fel.ear.carstatus;

import cz.cvut.fel.ear.carstatus.dao.BatteryDao;
import cz.cvut.fel.ear.carstatus.model.Battery;
import cz.cvut.fel.ear.carstatus.service.BatteryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarstatusApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarstatusApplication.class, args);
	}

}
