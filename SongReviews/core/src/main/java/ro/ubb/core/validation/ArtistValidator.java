package ro.ubb.core.validation;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.exceptions.ValidatorException;

import java.util.Optional;

@Component
public class ArtistValidator implements Validator<Artist>{


    /**
     * This function validates an entity which is supposed to be an artist.
     * In order for an artist to be valid: its ID must be a non-negative number,
     * its name must be between 5 and 100 characters and its description must be between 5 and 300 characters.
     *
     * @param entity: the supposed-to-be artist
     * @throws ValidatorException
     */
    @Override
    public void validate(Artist entity) throws ValidatorException {
        Optional.of(entity).filter(artist -> artist.getName().length() >= 5).orElseThrow(
                () -> new ValidatorException("Artist name should be longer than 5 characters.\n"));
        Optional.of(entity).filter(artist -> artist.getName().length() <= 100).orElseThrow(
               () -> new ValidatorException("Artist name should be shorter than 100 characters.\n"));
        Optional.of(entity).filter(artist -> artist.getDescription().length() >= 5).orElseThrow(
                () -> new ValidatorException("Artist description should be longer than 5 characters.\n"));
        Optional.of(entity).filter(artist -> artist.getDescription().length() <= 300).orElseThrow(
                () -> new ValidatorException("Artist description should be shorter than 300 characters.\n"));
    }
}
