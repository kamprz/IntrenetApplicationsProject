package wat.semestr8.tim.dtos.validators.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReadableDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadableDateConstraint {
    String message() default "Wrong date format. Used one is UTC ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}