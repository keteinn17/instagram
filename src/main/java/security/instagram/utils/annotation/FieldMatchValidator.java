package security.instagram.utils.annotation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object firstField = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
        Object secondField = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

        if (firstField != null) {
            return firstField.equals(secondField);
        } else {
            return secondField == null;
        }
    }
}