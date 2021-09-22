package ro.ubb.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.User;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.repository.user.UserRepository;
import ro.ubb.core.validation.UserValidator;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;

    @Autowired
    private UserValidator validator;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    /**
     * This function adds a user to the repository
     *
     * @param name: the name of the user
     * @throws AlreadyExistingElementException if the user (the ID) is already there
     */
    public void addUser(String name){
        log.trace("Adding user with name{}", name);
        User user = User.builder().name(name).build();
        validator.validate(user);
        User result = repository.save(user);
        log.trace("Added user with id{}", result.getId());
    }

    /**
     * This function removes a user from the repository based on their ID
     *
     * @param id: the ID of the user
     * @throws ElementNotFoundException if the user isn't found in the repository based on their ID
     */
    public void deleteUser(int id){
        log.trace("Removing user with id {}", id);
        repository.findById(id).orElseThrow(() -> new ElementNotFoundException("User with id " + id + " is not in the repository"));
        repository.deleteById(id);
        log.trace("Removed user with id {}", id);
    }

    /**
     * This function returns an iterable collection with the users that are currently in the repository
     *
     * @return all: an iterable collection of users
     */
    public Iterable<User> getUsers(){

        log.trace("getUsers --- method entered");

        var result =  repository.findAll();

        log.trace("getUsers: result={}", result);

        return result;
    }

    /**
     * This function updated a user based on their ID with a new name
     *
     * @param id:   the user's ID
     * @param name: the new name of the user
     * @throws ElementNotFoundException if the user isn't found in the repository based on their ID
     */
    @Transactional
    public void updateUser(int id, String name){
        log.trace("updateUser - method entered: id={}, name={}", id, name);

        repository.findById(id).ifPresentOrElse(
                user -> {
                  user.setName(name);
                },
                () -> {throw new ElementNotFoundException("User with id " + id + " does not exist.");}
        );
        log.trace("updateUser - method finished");
    }

    public List<User> getByExactName(String name){
        return repository.findByExactName(name);
    }

    public List<User> getLikeName(String name){
        return repository.findByNameLike(name);
    }

}
