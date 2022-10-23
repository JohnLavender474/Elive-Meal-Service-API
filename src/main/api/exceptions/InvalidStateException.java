package main.api.exceptions;

import java.util.Collection;

/**
 * Exception to be thrown when the program reaches an invalid state.
 */
public class InvalidStateException extends ErrorsException {

    /**
     * Instantiate new invalid input exception.
     *
     * @param errors the errors
     */
    public InvalidStateException(String... errors) {
        super(errors);
    }

    /**
     * Instantiate new invalid input exception.
     *
     * @param errors the errors
     */
    public InvalidStateException(Collection<String> errors) {
        super(errors);
    }

}
