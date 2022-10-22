package main.api.exceptions;

import java.util.Collection;

/** Exception to be thrown when provided input is incorrect. */
public class InvalidInputException extends ErrorsException {

    /**
     * Instantiate new invalid input exception.
     *
     * @param errors the errors
     */
    public InvalidInputException(String... errors) {
        super(errors);
    }

    /**
     * Instantiate new invalid input exception.
     *
     * @param errors the errors
     */
    public InvalidInputException(Collection<String> errors) {
        super(errors);
    }

}
