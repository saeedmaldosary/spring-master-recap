package com.saeedmaldosary.springmaster.person;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPersonRequest(Integer id, @NotEmpty(message = "Name must be not empty") @JsonGetter("fullName") String name,@Min(value = 16, message = "Age must be grater than 16") Integer age, @NotNull(message = "Gender must no be null") Gender gender) {

}
