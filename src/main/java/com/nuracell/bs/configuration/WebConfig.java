package com.nuracell.bs.configuration;

import com.nuracell.bs.entity.Drone;
import com.nuracell.bs.repository.DroneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@Configuration
public class WebConfig {
    @Bean
    CommandLineRunner commandLineRunner(DroneRepository droneRepository) {
        return args -> {
            System.out.println("Hello");
            droneRepository.save(new Drone(UUID.randomUUID().toString()));
        };
    }
}
