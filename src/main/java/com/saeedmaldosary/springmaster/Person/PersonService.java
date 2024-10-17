package com.saeedmaldosary.springmaster.Person;

import com.saeedmaldosary.springmaster.SortingOrder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {


    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons(SortingOrder sort
            , Integer limit){

        Stream<Person> sortedStream;

        if(sort == SortingOrder.ASC){
            sortedStream =  personRepository.getPeople().stream().sorted(Comparator.comparing(Person::id));
        } else {
            sortedStream = personRepository.getPeople().stream().sorted(Comparator.comparing(Person::id).reversed());
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
    public Optional<Person> getPersonById(Integer id){

        return personRepository.getPeople().stream().filter(p -> p.id().equals(id)).findFirst();

    }

    public void deletePersonById(Integer id){
        personRepository.getPeople().removeIf(person -> person.id().equals(id));
    }

    public void addPerson(NewPersonRequest person){
        System.out.println("====================");
        System.out.println(person.gender());
        System.out.println("====================");
        personRepository.getPeople().add(new Person(personRepository.getIdCounter().incrementAndGet(),person.name(),person.age(),person.gender()));
    }

    public void updatePersonById(Integer id,UpdatePerson request) {
        personRepository.getPeople().stream().filter(p -> p.id().equals(id)).findFirst().ifPresent(p -> {
                    var index = personRepository.getPeople().indexOf(p);
                    if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.name())) {
                        Person person = new Person(
                                p.id(),
                                request.name(),
                                p.age(),
                                p.gender()
                        );
                        personRepository.getPeople().set(index, person);

                    }
                }
        );
    }

}
