package com.topov.forum.validation;

import java.util.List;

public interface Validator<T> {
    ValidationResult validate(T data);
}
