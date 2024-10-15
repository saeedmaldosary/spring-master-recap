package com.saeedmaldosary.springmaster;

import jakarta.websocket.server.PathParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@SpringBootApplication
public class SpringMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMasterApplication.class, args);
	}

	public enum Gender {MALE,FEMALE}

	public enum SortingOrder {ASC,DESC}

	public record Person(int id,String name, int age, Gender gender) {

	}

	public static List<Person> people = new ArrayList<>();

	static {
		people.add(new Person(1,"John",20,Gender.MALE));
		people.add(new Person(2,"John",22,Gender.MALE));
	}

	@GetMapping
	public List<Person> getPersons(@RequestParam(value = "sort",required = false,defaultValue = "DESC")
										 SortingOrder sort
	,@RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){

		Stream<Person> sortedStream;

		if(sort == SortingOrder.ASC){
			sortedStream =  people.stream().sorted(Comparator.comparing(Person::id));
		} else {
				sortedStream = people.stream().sorted(Comparator.comparing(Person::id).reversed());
		}
		return sortedStream.limit((limit)).collect(Collectors.toList());
	}

	/*
	In the context of Java (which this code appears to be),
	Optional is a container object introduced in Java 8.
	 It's used to represent a value that may or may not be present.
	*/
	/*
	We can filter the data using @RequestParam
	example: http://localhost:8080/1?sort=asc
	*/
	@GetMapping("{id}")
	public Optional<Person> getPersonById(@PathVariable("id") Integer id){

		return people.stream().filter(person -> person.id == id).findFirst();
	}

	@DeleteMapping("{id}")
	public void deletePersonById(@PathVariable("id") Integer id){
		people.removeIf(person -> person.id == id);
	}

}
