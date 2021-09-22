package ro.ubb.core.repository.song;

import org.springframework.data.repository.query.Param;
import ro.ubb.core.domain.Song;

import java.util.List;

public interface SongCustomRepository {

    Long countAllWithExactTitle(@Param("title") String title);

    Long countAllWithTitleLike(@Param("title") String title);
}
