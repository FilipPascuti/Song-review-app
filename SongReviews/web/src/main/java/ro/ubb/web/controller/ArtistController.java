package ro.ubb.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.service.ArtistService;
import ro.ubb.web.converter.ArtistConverter;
import ro.ubb.web.dto.ArtistDto;
import ro.ubb.web.dto.UserDto;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {

    @Autowired
    private ArtistService service;

    @Autowired
    private ArtistConverter converter;

    private static final Logger log = LoggerFactory.getLogger(ArtistController.class);

    @RequestMapping(value = "artists", method = RequestMethod.GET)
    public List<ArtistDto> getArtists(){
        log.trace("fetching users");
        return converter.convertModelsToDtos(service.getArtists());
    }

    @RequestMapping(value = "artists/{id}", method = RequestMethod.GET)
    public ArtistDto getArtist(@PathVariable int id){
        log.trace("fetching artist");
        return converter.convertModelToDto(service.getArtist(id));
    }


    @RequestMapping(value = "artists/add", method = RequestMethod.POST)
    public ResponseEntity<?> addArtist(@RequestBody ArtistDto userDto){
        log.trace("adding a user");
        Artist artist = converter.convertDtoToModel(userDto);
        try{
            service.addArtist(artist.getName(), artist.getDescription());
        } catch (AlreadyExistingElementException e){
            log.trace("artist not added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("artist added");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "artists/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateArtist(@RequestBody ArtistDto artistDto){
        log.trace("updating a artist");
        Artist artist = converter.convertDtoToModel(artistDto);
        try{
            service.updateArtist(artist.getId(), artist.getName(), artist.getDescription());
        } catch (ElementNotFoundException e){
            log.trace("artist not updated");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("artist updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "artists/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArtist(@PathVariable int id){
        log.trace("deleting a artist");
        try{
            service.deleteArtist(id);
        } catch (ElementNotFoundException e){
            log.trace("artist not deleted");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("artist deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = {"artists/filter", "artists/filter/{name}"}, method = RequestMethod.GET)
    public List<ArtistDto> getFilteredArtists(@PathVariable(required = false) Optional<String> name){
        log.trace("filtering users");
        String nameValue = "";
        if(name.isPresent())
            nameValue = name.get();
        return converter.convertModelsToDtos(service.filterByName(nameValue));
    }

    @RequestMapping(value = "artists/sorted", method = RequestMethod.GET)
    public List<ArtistDto> getSortedArtists(){
        log.trace("filtering users");
        return converter.convertModelsToDtos(service.getSortedByName());
    }

    @RequestMapping(value = "artists/get-exact-name/{name}", method = RequestMethod.GET)
    public List<ArtistDto> getByExactName(@PathVariable String name){
        return converter.convertModelsToDtos(service.getByExactName(name));
    }

    @RequestMapping(value = "artists/get-like-name/{name}", method = RequestMethod.GET)
    public List<ArtistDto> getByNameLike(@PathVariable String name){
        return converter.convertModelsToDtos(service.getLikeName(name));
    }

}
