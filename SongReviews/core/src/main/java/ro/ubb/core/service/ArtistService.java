package ro.ubb.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.User;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.repository.artist.ArtistRepository;
import ro.ubb.core.validation.ArtistValidator;

import java.util.List;


@Service
public class ArtistService {

    @Autowired
    ArtistRepository repository;

    @Autowired
    ArtistValidator validator;

    private static final Logger log = LoggerFactory.getLogger(ArtistService.class);

    /**
     * This function adds an artist to the repository.
     *
     * @param name : the name of the artist
     * @param description : the description of the artist
     * @throws AlreadyExistingElementException if the artist (the ID) is already in the repository
     */
    public void addArtist(String name, String description) {
        log.trace("Adding artist with name{}, description{}", name, description);
        Artist artist = Artist.builder().name(name).description(description).build();
        validator.validate(artist);
        Artist result = repository.save(artist);
        log.trace("Added the artist with id{}", result.getId());
    }

    /**
     * This function removes an artist from the repository based on their ID
     *
     * @param artistId : the ID of the artist
     * @throws ElementNotFoundException if the artist (the ID) isn't found in the repository
     */
    public void deleteArtist(int artistId){
        log.trace("Removing artist with id {}", artistId);
        repository.findById(artistId).orElseThrow(() -> new ElementNotFoundException("Artist with id " + artistId + " does not exist"));
        repository.deleteById(artistId);
        log.trace("Removed artist with id {}", artistId);
    }


    /**
     * This function returns an iterable collection with all the artists that are in the repository
     *
     * @return all: an iterable collection of artists
     */
    public Iterable<Artist> getArtists(){
        log.trace("get Artists --- method entered ");
        var result = repository.findAll();
        log.trace("getArtists: result{}", result);
        return result;
    }

    /**
     * This function returns the artist with the id from the repository
     *
     * @return the artist with that id
     */
    public Artist getArtist(int id) {
        repository.findById(id).orElseThrow(() -> new ElementNotFoundException("Artist with id " + id + " does not exist"));
        return repository.findById(id).get();
    }

    /**
     * This function updates an artist name and description based on their ID
     *
     * @param id : the ID of the artist
     * @param newName : the new name of the artist
     * @param newDescription : the new description of the artist
     * @throws ElementNotFoundException if the artist (the ID) isn't found in the repository
     */
    @Transactional
    public void updateArtist(int id, String newName, String newDescription){
        log.trace("updateArtist - method entered: id={}, name={}, description{}", id, newName, newDescription);

        repository.findById(id).ifPresentOrElse(
                artist -> {
                    artist.setName(newName);
                    artist.setDescription(newDescription);
                },
                () -> {throw new ElementNotFoundException("Artist with id " + id + " does not exist");}
        );

        log.trace("updateArtist - method finished");
    }

    /**
     * This function returns the artists with the name containing the name from the repository
     *
     * @param name : string that the name must contain
     */
    public List<Artist> filterByName(String name) {
        return this.repository.findAllByNameContaining(name);
    }

    /**
     * This function returns the artists with the name containing the name from the repository
     *
     */
    public List<Artist> getSortedByName() {
        return this.repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }



    public List<Artist> getByExactName(String name){
        return repository.findByExactName(name);
    }

    public List<Artist> getLikeName(String name){
        return repository.findByNameLike(name);
    }


}
