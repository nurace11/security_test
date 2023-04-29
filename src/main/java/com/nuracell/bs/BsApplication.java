package com.nuracell.bs;

import com.github.javafaker.Faker;
import com.nuracell.bs.client.RestClient;
import com.nuracell.bs.entity.*;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableScheduling
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

			generateStudentsWIthIdCards(studentRepository);

//			sortingStudents(studentRepository);

//			studentsPagination(studentRepository);
		};
	}



	private void generateStudentsWIthIdCards(StudentRepository studentRepository) {
		Faker faker = new Faker();

		List<Student> students = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = "%s.%s@gmail.com".formatted(firstName, lastName);

			Student student = new Student(
					firstName,
					lastName,
					email,
					faker.number().numberBetween(17, 55)
			);

			student.addBook(new Book(UUID.randomUUID().toString(), LocalDateTime.now().minusDays(4)));
			student.addBook(new Book(UUID.randomUUID().toString(), LocalDateTime.now().minusDays(1)));
			student.addBook(new Book(UUID.randomUUID().toString(), LocalDateTime.now().minusYears(2)));

			student.setStudentIdCard(
					new StudentIdCard(
							UUID.randomUUID().toString().substring(0,15),
							student
					)
			);

			student.addEnrolment(new Enrolment(
					new EnrolmentId(i + 1L, i + 1L),
					student,
					new Course(
							UUID.randomUUID().toString(), UUID.randomUUID().toString().substring(0, 2).toUpperCase()
					),
					LocalDateTime.now()
			));

			students.add(student);
		}

		studentRepository.saveAll(students);
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

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
