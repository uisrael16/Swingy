package com.uisrael.Model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

public class ValidateUserInput {
    static Validator validator;

    public ValidateUserInput(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public boolean validate(Hero player) {
        boolean ok;
        ok = false;
        Set<ConstraintViolation<Hero>> validationErrors = validator.validate(player);
        if (!validationErrors.isEmpty()) {
            ok = true;
            Iterator<ConstraintViolation<Hero>> iterator = validationErrors.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Hero> error = iterator.next();
                System.out.println(String.format("%s - %s [%s]", error.getPropertyPath().toString(), error.getMessage(), player.getName()));
            }
            validationErrors.clear();
        }
        return (ok);
    }
}
