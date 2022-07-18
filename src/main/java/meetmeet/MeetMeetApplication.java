package meetmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
<<<<<<< HEAD
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
=======
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55

@SpringBootApplication
public class MeetMeetApplication {

	public static void main(String[] args) {
<<<<<<< HEAD
=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
		SpringApplication.run(MeetMeetApplication.class, args);
	}

}
