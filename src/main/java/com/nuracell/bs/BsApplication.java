package com.nuracell.bs;

import com.github.javafaker.Faker;
import com.nuracell.bs.client.RestClient;
import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.entity.Student;
import com.nuracell.bs.entity.StudentIdCard;
import com.nuracell.bs.repository.StudentIdCardRepository;
import com.nuracell.bs.repository.StudentRepository;
import com.nuracell.bs.service.AppUserDetailsService;
import com.nuracell.bs.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	public CommandLineRunner jpaTest(
			StudentRepository studentRepository,
			StudentIdCardRepository studentIdCardRepository) {
		return args -> {
//			generateRandomStudents(studentRepository);

			generateStudentsWIthIdCards(studentIdCardRepository);

//			sortingStudents(studentRepository);

//			studentsPagination(studentRepository);
		};
	}

	private void studentsPagination(StudentRepository studentRepository) {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("age"));
		Page<Student> page = studentRepository.findAll(pageRequest);
		System.out.println(page);
		page.forEach(s -> System.out.println(s.getId() + " " + s.getFirstName() + " " + s.getAge()));
	}

	private void sortingStudents(StudentRepository studentRepository) {
		//			Sort sort = Sort.by(Sort.Direction.ASC, "age");
		Sort sort = Sort.by("age").ascending()
				.and(Sort.by("firstName").descending());

		studentRepository
				.findAll(sort)
				.forEach(s -> System.out.println(s.getAge() + " " + s.getFirstName()));
	}

	private void generateRandomStudents(StudentRepository studentRepository) {
		Faker faker = new Faker();

		List<Student> students = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = "%s.%s@gmail.com".formatted(firstName, lastName);
			students.add(new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(17,55)
			));
		}

		studentRepository.saveAll(students);
	}

	private void generateStudentsWIthIdCards(StudentIdCardRepository studentIdCardRepository) {
		Faker faker = new Faker();

		List<StudentIdCard> studentIdCards = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = "%s.%s@gmail.com".formatted(firstName, lastName);
			studentIdCards.add(
					new StudentIdCard(
							UUID.randomUUID().toString().substring(0,15),
							new Student(
									firstName,
									lastName,
									email,
									faker.number().numberBetween(17,55)
							)

					)
			);
		}

		studentIdCardRepository.saveAll(studentIdCards);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
