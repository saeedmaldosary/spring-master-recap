package com.saeedmaldosary.springmaster.person;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record Person(Integer id, @JsonGetter("fullName") String name, Integer age,
                     @JsonIgnore Gender gender) {

}
