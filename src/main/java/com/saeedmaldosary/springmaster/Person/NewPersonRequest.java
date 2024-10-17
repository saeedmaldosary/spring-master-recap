package com.saeedmaldosary.springmaster.Person;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPersonRequest(Integer id, @NotEmpty @JsonGetter("fullName") String name,@Min(16) Integer age,
                               @NotNull Gender gender) {

}
