package dev.phelliperodrigues.volunteerAccessoryApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.plaf.PanelUI;

@SpringBootApplication
public class VolunteerAccessoryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolunteerAccessoryApiApplication.class, args);
	}


	public void testApplication(Integer number) {

		if (number <0) {
			System.out.println("Number is negative");
		} else if (number > 0) {
			System.out.println("Number is positive");
		} else {
			System.out.println("Number is zero");
		}
	}

}
