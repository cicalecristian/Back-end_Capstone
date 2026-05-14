package cristiancicale.capstone;

import cristiancicale.capstone.services.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CapstoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneApplication.class, args);
    }

    @Bean
    CommandLineRunner run(
            SeedService seedService
    ) {

        return args -> {
            seedService.seedDatabase();
        };
    }
}
