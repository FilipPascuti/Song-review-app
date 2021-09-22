package ro.ubb.core.repository.song;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Song;
import ro.ubb.core.domain.User;
import ro.ubb.core.repository.Repository;

import java.util.List;
import java.util.Optional;

//@Component("SongJPQLRepository")
@Component("SongCriteriaRepository")
//@Component("SongNativeRepository")
public interface SongRepository extends Repository<Song, Integer>, SongCustomRepository {

    @Query("select distinct song from Song song")
    @EntityGraph(value = "songWithRentalsAndUsers", type = EntityGraph.EntityGraphType.LOAD)
    List<Song> findAllSongsWithRentals();

    @Query("select distinct song from Song song where song.id=:songId")
    @EntityGraph(value = "songWithRentalsAndUsers", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Song> findByIdWithReviews(@Param("songId") Integer songId);
}
