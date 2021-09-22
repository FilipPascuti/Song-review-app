package ro.ubb.core.validation;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.User;
import ro.ubb.core.exceptions.ValidatorException;

import java.util.Optional;

@Component
public class UserValidator implements Validator<User>{

    /**
     * This function validates an entity which is supposed to be a user
     * In order for a user to be valid, its ID must be an non-negative number and its name be between 5 and 100 characters
     * @param entity: the supposed-to-be user
     * @throws ValidatorException
     */
    @Override
    public void validate(User entity) throws ValidatorException {
//        Optional.of(entity).filter(user -> user.getId() >= 0).orElseThrow(() -> new ValidatorException("User ID should have a value greater than 0"));
        Optional.of(entity).filter(user -> user.getName().length() >= 5 && user.getName().length() <=100).orElseThrow(() -> new ValidatorException("User name should have a number of characters between 5 and 100"));
    }
}
