package com.saeedmaldosary.springmaster.person;

public record PersonUpdateRequest(
        String name,
        Integer age,
        String email
) {
}