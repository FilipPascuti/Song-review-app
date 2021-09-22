package ro.ubb.core.repository;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.ITConfig;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.Song;
import ro.ubb.core.domain.SongDetails;
import ro.ubb.core.domain.User;
import ro.ubb.core.repository.song.SongRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-songs.xml")
public class SongRepositoryTest {

    @Autowired
    SongRepository repository;

    @Test
    public void findAll() throws Exception{
        List<Song> songs = repository.findAll();
        assertEquals("there should be 3 users", 3, songs.size());
    }

    @Test
    public void add() throws Exception {
        Artist artist = repository.findAll().get(0).getArtist();
        Song song = Song.builder()
                .details(new SongDetails("Turtles", 100, 'A'))
                .artist(artist)
                .build();
        song.setId(-1);
        repository.save(song);
        List<Song> songs = repository.findAll();
        assertEquals("there should be 4 users instead of 3", 4, songs.size());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        Song song = repository.findById(1).orElseThrow(RuntimeException::new);
        song.setDetails(new SongDetails("Marea", 200, 'B'));
        List<Song> songs = repository.findAll();
        assertEquals("name updated corectly", "Marea", songs.get(0).getDetails().getTitle());
        assertEquals("name updated corectly", 200, songs.get(0).getDetails().getLength());
        assertEquals("name updated corectly", "B", songs.get(0).getDetails().getKey().toString());
    }

    @Test
    public void delete() throws Exception {
        repository.deleteById(2);
        List<Song> songs= repository.findAll();
        assertEquals("the size show now be 2", 2, songs.size());
    }

}
