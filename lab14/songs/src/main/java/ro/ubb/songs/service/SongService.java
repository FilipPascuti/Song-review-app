package ro.ubb.songs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.songs.domain.Artist;
import ro.ubb.songs.domain.Song;
import ro.ubb.songs.domain.SongDetails;
import ro.ubb.songs.dto.SongDto;
import ro.ubb.songs.exceptions.ElementNotFoundException;
import ro.ubb.songs.repository.SongRepository;
import ro.ubb.songs.validator.SongValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class SongService {

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongValidator validator;

    @Autowired
    RestTemplate restTemplate;

    public static final Logger log = LoggerFactory.getLogger(SongService.class);

    /**
     * This function adds a new song to the repository
     * @param title: the title of the song
     * @param length: the length of the song
     * @param key: the key of the song
     * @param artistID: the ID of the artist that sings the song
     * @throws ElementNotFoundException if the artistID does not exist in the artist repository
     */
    @Transactional
    public void addSong(String title, Integer length, Character key, Integer artistID){
        log.trace("Adding song with title{}, length{}, key{}, artistID{}", title, length, key, artistID);

        String host = System.getenv("GATEWAY");

        try {
            Artist artist =
                    restTemplate.getForObject("http://" + host + ":8080/api/artists/" + artistID,
                            Artist.class);
        } catch (RestClientException exception) {
            throw new ElementNotFoundException("artist does not exist");
        }

        Song song = Song.builder()
                .details(new SongDetails(title, length, key))
                .artistId(artistID)
                .build();
        songRepository.save(song);
        log.trace("Added the song successfully ");
    }

    /**
     * This function deletes a song from the repository based on their ID
     * @param id: the ID of the song
     * @throws ElementNotFoundException if the song ID does not exist in the song repository
     */
    @Transactional
    public void deleteSong(Integer id){
        log.trace("Removing song with id {}", id);

        songRepository.findById(id).orElseThrow(RuntimeException::new);
        songRepository.deleteById(id);

        log.trace("Removed song with id {}", id);
    }

    /**
     * This function returns an iterable collection with the songs that are in the song repository
     *
     * @return all: an iterable collection of users
     */
    public List<SongDto> getSongs(){

        log.trace("getSongs --- method entered");

        String host = System.getenv("GATEWAY");

        var result = songRepository.findAll().stream()
                .map(song -> {

                    Artist artist =
                            restTemplate.getForObject("http://" + host +":8080/api/artists/" + song.getArtistId(),
                                    Artist.class);

                    return SongDto.builder()
                            .id(song.getId())
                            .details(song.getDetails())
                            .artist(artist)
                            .build();
                })
                .collect(Collectors.toList());

        log.trace("getSongs: result={}", result);

        return result;
    }

    /**
     * This function updates a song based on their id with new attributes
     *
     * @param id: the ID of the song
     * @param title: the new title of the song
     * @param length: the new length of the song
     * @param key: the new key of the song
     * @throws ElementNotFoundException if the song does not exist in the song repository
     */
    @Transactional
    public void updateSong(Integer id, String title, Integer length, Character key){

        log.trace("updateSong - method entered: id={}, title={}, length{}, key{}", id, title, length, key);

        songRepository.findById(id).ifPresentOrElse(
                song -> {
                    song.getDetails().setTitle(title);
                    song.getDetails().setLength(length);
                    song.getDetails().setKey(key);
                },
                () -> {throw new ElementNotFoundException("Song with id " + id + " does not exist");}
        );

        log.trace("updated song with id={}", id);

    }

    /**
     * This function returns the song with the id from the repository
     *
     * @return the artist with that id
     */
    public SongDto getSong(int id) {
        log.trace("getSongs --- method entered");

        Song song = songRepository.findById(id).orElseThrow(RuntimeException::new);

        String host = System.getenv("GATEWAY");

        Artist artist =
                restTemplate.getForObject("http://" + host +":8080/api/artists/" + song.getArtistId(),
                        Artist.class);

        return SongDto.builder()
                .id(song.getId())
                .details(song.getDetails())
                .artist(artist)
                .build();
    }

}
