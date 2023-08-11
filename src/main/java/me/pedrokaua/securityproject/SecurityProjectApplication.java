package me.pedrokaua.securityproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SecurityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityProjectApplication.class, args);
	}

}
