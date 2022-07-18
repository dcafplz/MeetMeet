package meetmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "meetmeet.controller", "meetmeet.service" })
@EnableJpaRepositories(basePackages = { "meetmeet.model.dao" })
@EntityScan(basePackages = { "meetmeet.model.entity" })
public class MeetMeetApplication {

	public static void main(String[] args) {
		System.out.println("MeetMeetApplication 시작");
		SpringApplication.run(MeetMeetApplication.class, args);

	}
}
