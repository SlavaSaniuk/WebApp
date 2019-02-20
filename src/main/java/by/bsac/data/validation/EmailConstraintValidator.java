package by.bsac.data.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 *
 */
public class EmailConstraintValidator implements ConstraintValidator<Email, String> {

    private final Pattern EMAIL_SYNTAX_PATTERN = Pattern.compile("(\\w+[\\.\\-#&\\[\\]\\{\\}\\*\\+\\?\\(\\)]?\\w+)+" +
                    "@(\\w+[\\.\\-#&\\[\\]\\{\\}\\*\\+\\?\\(\\)]?\\w+)+" +
                    "\\.[a-z]{2,4}", Pattern.CASE_INSENSITIVE);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(EMAIL_SYNTAX_PATTERN.pattern(), value);
    }
}
