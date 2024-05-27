package security.instagram.utils.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String message() default "Fields must match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();
}
