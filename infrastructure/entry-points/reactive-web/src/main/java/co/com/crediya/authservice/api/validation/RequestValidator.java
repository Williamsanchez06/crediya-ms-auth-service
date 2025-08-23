package co.com.crediya.authservice.api.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Map<String, String> validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<T> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String jsonName = resolveJsonPropertyName(dto.getClass(), fieldName);
            errors.put(jsonName, violation.getMessage());
        }

        return errors;
    }

    private String resolveJsonPropertyName(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            if (annotation != null) {
                return annotation.value();
            }
        } catch (NoSuchFieldException ignored) {
        }
        return fieldName;
    }
}
