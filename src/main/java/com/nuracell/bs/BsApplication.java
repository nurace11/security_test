package com.nuracell.bs;

import com.nuracell.bs.client.RestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BsApplication {
	public static void main(String[] args) {
		SpringApplication.run(BsApplication.class, args);
	}

	@Bean
	public CommandLineRunner clr(RestClient restClient) {
		return args -> {
			System.out.println("Hello from BsApplication.java again arrre");

			restClient.test();
//			ApplicationContext appContext = new AnnotationConfigApplicationContext();
//			System.out.println("appContext.getApplicationName() = " + appContext.getApplicationName());
//			System.out.println("appContext.getDisplayName() = " + appContext.getDisplayName());
//			System.out.println("appContext.getBean(\"clr\") = " + appContext.getBean("clr"));
		};
	}


}
