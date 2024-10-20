package com.saeedmaldosary.springmaster.person;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Integer> {

    boolean existsByEmail(String email);
}

