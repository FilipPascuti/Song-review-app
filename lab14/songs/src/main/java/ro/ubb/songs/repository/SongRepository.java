package ro.ubb.songs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.songs.domain.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {
}
