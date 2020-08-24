package com.topov.forum.dto.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationErrorTest {
    @Test
    void bindingResultToMapOfStringListStringConversion() {
        final String field1 = "field 1";
        final String field2 = "field 2";
        final String field1Error1 = "field 1 error 1";
        final String field1Error2 = "field 1 error 2";
        final String field1Error3 = "field 2 error 1";

        final FieldError fieldError1 = mock(FieldError.class);
        when(fieldError1.getField()).thenReturn(field1);
        when(fieldError1.getDefaultMessage()).thenReturn(field1Error1);

        final FieldError fieldError2 = mock(FieldError.class);
        when(fieldError2.getField()).thenReturn(field1);
        when(fieldError2.getDefaultMessage()).thenReturn(field1Error2);

        final FieldError fieldError3 = mock(FieldError.class);
        when(fieldError3.getField()).thenReturn(field2);
        when(fieldError3.getDefaultMessage()).thenReturn(field1Error3);

        final BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2, fieldError3));

        final ValidationError response = new ValidationError(bindingResultMock);
        final Map<String, List<String>> inputErrors = response.getInputErrors();

        assertEquals(2, inputErrors.get(field1).size());
        assertEquals(1, inputErrors.get(field2).size());
        assertTrue(inputErrors.get(field1).contains(field1Error1));
        assertTrue(inputErrors.get(field1).contains(field1Error2));
        assertTrue(inputErrors.get(field2).contains(field1Error3));
    }
}
