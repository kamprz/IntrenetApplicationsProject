package wat.semestr8.tim.dtos.validators.purchase;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReadableDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadableDateConstraint {
    String message() default "Purchase must have userId or email set.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}