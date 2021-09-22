package ro.ubb.core.validation;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Song;
import ro.ubb.core.exceptions.ValidatorException;

import java.util.Optional;

@Component
public class SongValidator implements Validator<Song>{
    /**
     * This function validates an entity which is supposed to be a song
     * In order for a song to be valid, the ID must be a non-negative number, the size of the title should be between 1 and 100,
     * the length of the song should be grater than 30 and less than 600 seconds, the key should be between A and G and
     * the artist ID should be a non-negative number
     * @param entity: the supposed-to-be song
     * @throws ValidatorException
     */

    @Override
    public void validate(Song entity) throws ValidatorException {
        Optional.of(entity).filter(song -> song.getId() >= 0).orElseThrow(() -> new ValidatorException("Song ID should have a value greater than 0"));
        Optional.of(entity).filter(song -> song.getDetails().getTitle().length() > 0 && song.getDetails().getTitle().length() <= 100)
                            .orElseThrow(() -> new ValidatorException("Song title should have a size between 0 and 100"));
        Optional.of(entity).filter(song -> song.getDetails().getLength() >= 30 && song.getDetails().getLength() <= 600)
                           .orElseThrow(() -> new ValidatorException("Song length should be more than 30 and less than 600 seconds"));
        Optional.of(entity).filter(song -> song.getDetails().getKey() >= 'A' && song.getDetails().getKey() <= 'G')
                           .orElseThrow(() -> new ValidatorException("Song key should be between A and K"));
        Optional.of(entity).filter(song -> song.getArtist().getId() >= 0).orElseThrow(() -> new ValidatorException("Artist ID in Song should have a value greater than 0"));
    }

}
