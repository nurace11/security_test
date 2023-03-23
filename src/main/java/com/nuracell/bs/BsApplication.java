package com.nuracell.bs;

import com.nuracell.bs.client.RestClient;
import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.service.AppUserDetailsService;
import com.nuracell.bs.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BsApplication.class, args);
	}

	@Bean
	public CommandLineRunner clr(RestClient restClient,
								 AppUserDetailsService appUserDetailsService,
								 PasswordEncoder passwordEncoder) {
		return args -> {
			System.out.println("Hello from BsApplication.java again arrre");
			appUserDetailsService.addAppUser(new AppUser("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
//			restClient.testPlayerREST();
		};
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
