package ro.ubb.core.validation;

import ro.ubb.core.exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
