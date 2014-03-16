package eu.com.cwsfe.contact;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Radoslaw Osinski
 */
class ContactEmailValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ContactMail.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NameMustBeSet");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EmailIsRequired");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "MessageIsRequired");
        ContactMail contactMail = (ContactMail) o;
        if(!EmailValidator.isValidEmailAddress(contactMail.email)) {
            errors.rejectValue("email", "EmailIsInvalid");
        }
    }

}
