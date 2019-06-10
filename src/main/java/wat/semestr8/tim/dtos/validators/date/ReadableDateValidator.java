package wat.semestr8.tim.dtos.validators.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReadableDateValidator implements
        ConstraintValidator<ReadableDateConstraint, String> {

    final String dateFormatRegex = "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3} UTC";

    @Override
    public void initialize(ReadableDateConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String date,
                           ConstraintValidatorContext cxt) {
        return date != null && date.matches(dateFormatRegex);
    }

}
