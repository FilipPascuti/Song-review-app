package ro.ubb.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.Song;
import ro.ubb.core.domain.SongDetails;
import ro.ubb.core.domain.User;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.repository.artist.ArtistRepository;
import ro.ubb.core.repository.song.SongRepository;
import ro.ubb.core.validation.SongValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongValidator validator;

    public static final Logger log = LoggerFactory.getLogger(SongService.class);

    /**
     * This function checks whether an artistID exists in the artist repository
     * @param artistID: the ID of the artist we will do the checking
     * @throws ElementNotFoundException if the artistID does not exist in the artist repository
     */
    private void checkArtistID(Integer artistID){
        artistRepository.findById(artistID)
                .orElseThrow(() -> new ElementNotFoundException("Artist with ID " + artistID + " not found"));
    }

    /**
     * This function adds a new song to the repository
     * @param title: the title of the song
     * @param length: the length of the song
     * @param key: the key of the song
     * @param artistID: the ID of the artist that sings the song
     * @throws AlreadyExistingElementException if the user( i.e. the ID) already exist
     * @throws ElementNotFoundException if the artistID does not exist in the artist repository
     */
    @Transactional
    public void addSong(String title, Integer length, Character key, Integer artistID){
        log.trace("Adding song with title{}, length{}, key{}, artistID{}", title, length, key, artistID);
        checkArtistID(artistID);

        Song song = Song.builder()
                .details(new SongDetails(title, length, key))
                .artist(artistRepository.findById(artistID).get())
                .build();
        artistRepository.findByIdWithSongs(artistID).orElseThrow(RuntimeException::new).getSongs().add(song);
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

        Optional<Song> optionalSong = artistRepository.findAllArtistsWithSongs().stream()
                .map(artist -> artist.getSongs())
                .flatMap(songs -> songs.stream())
                .filter(song -> song.getId().equals(id))
                .findFirst();
        optionalSong.ifPresent(
                song -> {
                    Optional<Artist> artist = artistRepository.findByIdWithSongs(song.getArtist().getId());
                    artist.ifPresent(a -> a.getSongs().remove(song));
                });

        log.trace("Removed song with id {}", id);
    }

    /**
     * This function returns an iterable collection with the songs that are in the song repository
     *
     * @return all: an iterable collection of users
     */
    public Iterable<Song> getSongs(){

        log.trace("getSongs --- method entered");

        var result = artistRepository.findAllArtistsWithSongs().stream()
                .map(artist -> artist.getSongs())
                .reduce(new ArrayList<>(), (a,b) -> {
                    a.addAll(b);
                    return a;
                });

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

        artistRepository.findAllArtistsWithSongs().stream()
                .map(artist -> artist.getSongs())
                .reduce(new ArrayList<>(), (a,b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(song -> song.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
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
    public Song getSong(int id) {
        return artistRepository.findAllArtistsWithSongs().stream()
                .map(artist -> artist.getSongs())
                .reduce(new ArrayList<>(), (a,b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(song -> song.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> {throw new ElementNotFoundException("Song with id " + id + " does not exist");}
                );
    }

    public Long getByExactName(String name){
        return songRepository.countAllWithExactTitle(name);
    }

    public Long getLikeName(String name){
        return songRepository.countAllWithTitleLike(name);
    }
}
