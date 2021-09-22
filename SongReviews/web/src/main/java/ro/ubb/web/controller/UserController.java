package ro.ubb.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.domain.User;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.service.UserService;
import ro.ubb.web.converter.UserConverter;
import ro.ubb.web.dto.UserDto;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserConverter converter;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<UserDto> getUsers(){
        log.trace("fetching users");
        return converter.convertModelsToDtos(service.getUsers());
    }

    @RequestMapping(value = "users/add", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        log.trace("adding a user");
        User user = converter.convertDtoToModel(userDto);
        try{
            service.addUser(user.getName());
        } catch (AlreadyExistingElementException e){
            log.trace("user not added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("user added");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "users/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto){
        log.trace("updating a user");
        User user = converter.convertDtoToModel(userDto);
        try{
            service.updateUser(user.getId(), user.getName());
        } catch (ElementNotFoundException e){
            log.trace("user not updated");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("user updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "users/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        log.trace("deleting a user");
        try{
            service.deleteUser(id);
        } catch (ElementNotFoundException e){
            log.trace("user not deleted");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("user deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "users/get-exact-name/{name}", method = RequestMethod.GET)
    public List<UserDto> getByExactName(@PathVariable String name){
        return converter.convertModelsToDtos(service.getByExactName(name));
    }

    @RequestMapping(value = "users/get-like-name/{name}", method = RequestMethod.GET)
    public List<UserDto> getByNameLike(@PathVariable String name){
        return converter.convertModelsToDtos(service.getLikeName(name));
    }
}
