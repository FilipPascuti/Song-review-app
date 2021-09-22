package ro.ubb.core.repository.artist;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.repository.Repository;

import java.util.List;
import java.util.Optional;

//@Component("ArtistJPQLRepository")
//@Component("ArtistCriteriaRepository")
@Component("ArtistNativeRepository")
public interface ArtistRepository extends Repository<Artist, Integer>, ArtistCustomRepository {
    List<Artist> findAllByNameContaining(String name);

    @Query("select distinct artist from Artist artist")
    @EntityGraph(value = "artistWithSongs", type = EntityGraph.EntityGraphType.LOAD)
    List<Artist> findAllArtistsWithSongs();

    @Query("select distinct artist from Artist artist where artist.id=:artistId")
    @EntityGraph(value = "artistWithSongs", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Artist> findByIdWithSongs(@Param("artistId") Integer artistId);
}
