package gdsc.mju.guessme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GuessmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuessmeApplication.class, args);
	}

}
