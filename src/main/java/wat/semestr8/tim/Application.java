package wat.semestr8.tim;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import wat.semestr8.tim.dtos.PurchaseDto;
import wat.semestr8.tim.dtos.TicketDto;
import wat.semestr8.tim.utils.DateUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}

//odpalanie pgadmin4:
// ~/pgadmin4/pgadmin4
