package com.saeedmaldosary.springmaster.Person;

import com.saeedmaldosary.springmaster.SortingOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public List<Person> getPersons(@RequestParam(value = "sort",required = false,defaultValue = "DESC") SortingOrder sort
            , @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit){

     return personService.getPersons(sort,10);
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

        Optional<Person> person = personService.getPersonById(id);
        return ResponseEntity.status(200).body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@PathVariable("id") Integer id){

        personService.deletePersonById(id);
    }

    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @PutMapping("{id}")
    public void updatePersonById(@PathVariable Integer id,@RequestBody UpdatePerson request) {
   personService.updatePersonById(id, request);
    }

}
