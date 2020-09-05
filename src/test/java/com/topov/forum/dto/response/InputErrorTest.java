package com.topov.forum.dto.response;

import com.topov.forum.dto.error.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationErrorTest {
    @Test
    void bindingResultToMapOfStringListStringConversion() {
        final String field1 = "field 1";
        final String field2 = "field 2";
        final String field1Error1 = "field 1 error 1";
        final String field1Error2 = "field 1 error 2";
        final String field2Error1 = "field 2 error 1";

        final FieldError fieldError1 = mock(FieldError.class);
        when(fieldError1.getField()).thenReturn(field1);
        when(fieldError1.getDefaultMessage()).thenReturn(field1Error1);

        final FieldError fieldError2 = mock(FieldError.class);
        when(fieldError2.getField()).thenReturn(field1);
        when(fieldError2.getDefaultMessage()).thenReturn(field1Error2);

        final FieldError fieldError3 = mock(FieldError.class);
        when(fieldError3.getField()).thenReturn(field2);
        when(fieldError3.getDefaultMessage()).thenReturn(field2Error1);

        final BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2, fieldError3));

        final InputErrors response = new InputErrors(bindingResultMock);
        final List<ValidationError> errors = response.getErrors();

        final ValidationError e1 = errors.get(0);
        assertEquals(e1.getInvalidProperty(), field1);
        assertEquals(e1.getError(), field1Error1);
        final ValidationError e2 = errors.get(1);
        assertEquals(e2.getInvalidProperty(), field1);
        assertEquals(e2.getError(), field1Error2);
        final ValidationError e3 = errors.get(2);
        assertEquals(e3.getInvalidProperty(), field2);
        assertEquals(e3.getError(), field2Error1);
    }
}
