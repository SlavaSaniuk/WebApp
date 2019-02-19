package by.bsac.exceptions;

public class AuthenticationException extends WebAppException {


    public AuthenticationException(String a_message) {
        super(a_message);
    }

    public AuthenticationException(AuthenticationMessages code) {
        super(code.toString());
    }
}
