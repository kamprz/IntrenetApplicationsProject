package wat.semestr8.tim.dtos.validators.purchase;

import wat.semestr8.tim.dtos.PurchaseDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PurchaseValidator implements
        ConstraintValidator<PurchaseConstraint, PurchaseDto> {
    @Override
    public void initialize(PurchaseConstraint contactNumber) {
    }

    @Override
    public boolean isValid(PurchaseDto value, ConstraintValidatorContext context) {
        return !(value.getEmail() == null && value.getUserId() == null);
    }

}
