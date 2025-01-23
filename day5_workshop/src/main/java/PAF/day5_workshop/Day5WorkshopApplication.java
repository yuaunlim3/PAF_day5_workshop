package PAF.day5_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import PAF.day5_workshop.Repository.RedisRepository;

@SpringBootApplication
public class Day5WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day5WorkshopApplication.class, args);
	}

}
