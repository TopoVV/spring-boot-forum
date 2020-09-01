package com.topov.forum.validation;

public interface Validator<T> {
    ValidationResult validate(T data);
}
