package residentevil_app.domain.custom_annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class PresentOrFutureValidator implements ConstraintValidator<PresentOrFuture, Date> {
    @Override
    public boolean isValid(Date date, //TODO date is null
                           ConstraintValidatorContext constraintValidatorContext) {
        Date today = new Date();
        return date.after(today);
    }
}

