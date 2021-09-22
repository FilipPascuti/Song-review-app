package ro.ubb.core.repository.artist;

import org.springframework.data.repository.query.Param;
import ro.ubb.core.domain.Artist;

import java.util.List;

public interface ArtistCustomRepository {

    List<Artist> findByExactName(@Param("name") String name);

    List<Artist> findByNameLike(@Param("name") String name);

}
