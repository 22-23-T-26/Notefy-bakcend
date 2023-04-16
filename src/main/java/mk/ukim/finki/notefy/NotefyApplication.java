package mk.ukim.finki.notefy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


/**
 * TODO exclude = SecurityAutoConfiguration.class remove after config of security
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class NotefyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotefyApplication.class, args);
	}

}
