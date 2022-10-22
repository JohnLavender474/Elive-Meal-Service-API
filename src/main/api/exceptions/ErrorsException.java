package main.api.exceptions;

import java.util.*;

/** An exception object that can hold more than one error message. */
public class ErrorsException extends RuntimeException {

    private final List<String> errors = new ArrayList<>();

    /**
     * Instantiate an error exception with the following errors.
     *
     * @param errors the errors
     */
    public ErrorsException(String... errors) {
        this(Arrays.asList(errors));
    }

    /**
     * Instantiate an error exception with the following errors.
     *
     * @param errors the errors
     */
    public ErrorsException(Collection<String> errors) {
        this.errors.addAll(errors);
    }

    /**
     * Get an unmodifiable view of the errors.
     *
     * @return the errors
     */
    public Collection<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
