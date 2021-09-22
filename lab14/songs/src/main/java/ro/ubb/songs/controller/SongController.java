package ro.ubb.songs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.songs.converter.SongConverter;
import ro.ubb.songs.domain.Song;
import ro.ubb.songs.dto.SongDto;
import ro.ubb.songs.exceptions.AlreadyExistingElementException;
import ro.ubb.songs.exceptions.ElementNotFoundException;
import ro.ubb.songs.service.SongService;

import java.util.List;

@RestController
public class SongController {

    @Autowired
    SongService service;

    @Autowired
    SongConverter converter;

    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    @RequestMapping(value = "songs", method = RequestMethod.GET)
    public List<SongDto> getSongs(){
        log.trace("fetching songs");
        return service.getSongs();
    }

    @RequestMapping(value = "songs/{id}", method = RequestMethod.GET)
    public SongDto getSong(@PathVariable int id){
        log.trace("fetching song");
        return service.getSong(id);
    }

    @RequestMapping(value = "songs/add", method = RequestMethod.POST)
    public ResponseEntity<?> addSong(@RequestBody SongDto songDto){
        log.trace("adding a song");
        Song song = converter.convertDtoToModel(songDto);
        try{
            service.addSong(song.getDetails().getTitle(), song.getDetails().getLength(), song.getDetails().getKey(), song.getArtistId());
        } catch (AlreadyExistingElementException e){
            log.trace("song not added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("song added");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "songs/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSong(@RequestBody SongDto songDto){
        log.trace("updating a song");
        Song song = converter.convertDtoToModel(songDto);
        try{
            service.updateSong(song.getId(), song.getDetails().getTitle(), song.getDetails().getLength(), song.getDetails().getKey());
        } catch (ElementNotFoundException e){
            log.trace("song not updated");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("song updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "songs/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSong(@PathVariable int id){
        log.trace("deleting a song");
        try{
            service.deleteSong(id);
        } catch (ElementNotFoundException e){
            log.trace("song not deleted");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("song deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
