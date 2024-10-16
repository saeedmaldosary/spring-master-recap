package com.saeedmaldosary.springmaster;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.websocket.server.PathParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/person")
@SpringBootApplication
public class SpringMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMasterApplication.class, args);
	}

	public enum Gender {MALE,FEMALE}

	public enum SortingOrder {ASC,DESC}

	public record Person(Integer id, @JsonGetter("fullName") String name, Integer age,
						 @JsonIgnore Gender gender) {

	}

	public record UpdatePerson(String name, int age) {

	}

	public static List<Person> people = new ArrayList<>();

	private static AtomicInteger idCounter = new AtomicInteger(0);

	static {
		people.add(new Person(idCounter.incrementAndGet(),"John",20,Gender.MALE));
		people.add(new Person(idCounter.incrementAndGet(),"Saeed",22,Gender.MALE));
	}

	@GetMapping("/")
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
	public ResponseEntity<Optional<Person>> getPersonById(@PathVariable("id") Integer id){

		Optional<Person> person = people.stream().filter(p -> p.id.equals(id)).findFirst();
		return ResponseEntity.status(200).body(person);
	}

	@DeleteMapping("{id}")
	public void deletePersonById(@PathVariable("id") Integer id){
		people.removeIf(person -> person.id.equals(id));
	}

	@PostMapping
	public void addPerson(@RequestBody Person person){
		people.add(new Person(idCounter.incrementAndGet(),person.name,person.age,person.gender));
	}

	@PutMapping("{id}")
	public void updatePersonById(@PathVariable Integer id,@RequestBody UpdatePerson request) {
		people.stream().filter(p -> p.id.equals(id)).findFirst().ifPresent(p -> {
					var index = people.indexOf(p);
					if (request.name != null && !request.name.isEmpty() && !request.name.equals(p.name)) {
						Person person = new Person(
								p.id,
								request.name,
								p.age(),
								p.gender()
						);
						people.set(index, person);

					}
				}
		);
	}

}
