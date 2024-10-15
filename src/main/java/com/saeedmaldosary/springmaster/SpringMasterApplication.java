package com.saeedmaldosary.springmaster;

import jakarta.websocket.server.PathParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@SpringBootApplication
public class SpringMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMasterApplication.class, args);
	}

	public enum Gender {MALE,FEMALE}

	public record Person(int id,String name, int age, Gender gender) {

	}

	public static List<Person> people = new ArrayList<>();

	static {
		people.add(new Person(1,"John",20,Gender.MALE));
	}

	@GetMapping
	public List<Person> getPersons(){
		return people;
	}

	/* In the context of Java (which this code appears to be),
	Optional is a container object introduced in Java 8.
	 It's used to represent a value that may or may not be present.
	 */
	@GetMapping("{id}")
	public Optional<Person> getPersonById(@PathVariable("id") Integer id){
		return people.stream().filter(person -> person.id == id).findFirst();
	}

}
