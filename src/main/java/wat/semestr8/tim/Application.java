package wat.semestr8.tim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import wat.semestr8.tim.socket.service.model.SocketMessage;

@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class Application {
	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);
		SocketMessage.MessageType type = SocketMessage.MessageType("")
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
