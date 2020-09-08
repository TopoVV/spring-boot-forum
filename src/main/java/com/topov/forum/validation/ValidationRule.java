package com.topov.forum.validation;

import javax.validation.groups.Default;

public abstract class ValidationRule {
    public Class<?> getValidationSequence() {
        return Default.class;
    }
}
