package com.nuracell.bs.configuration.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "data")
public class FirstExample {
    String creationDate;
    Long longNumber;
}
