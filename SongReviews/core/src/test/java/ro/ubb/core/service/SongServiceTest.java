package ro.ubb.core.service;

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
import ro.ubb.core.ITConfig;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.Song;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-songs.xml")
public class SongServiceTest {

    @Autowired
    SongService service;

    @Test
    public void getUsers() throws Exception {
        Iterable<Song> songs = service.getSongs();
        long size = StreamSupport.stream(songs.spliterator(), false).count();
        assertEquals("there should be 3 songs", 3, size);
    }

    @Test
    public void addUser() throws Exception {
        service.addSong("Turtles", 100, 'A', 1);
        Iterable<Song> songs = service.getSongs();
        long size = StreamSupport.stream(songs.spliterator(), false).count();

        assertEquals("added new song, 4 instead of 3 songs", 4, size);
    }

    @Test
    public void deleteUser() throws Exception {
        service.deleteSong(1);
        Iterable<Song> songs = service.getSongs();
        long size = StreamSupport.stream(songs.spliterator(), false).count();

        assertEquals("deleted a song", 2, size);
    }

    @Test
    public void updateUser() throws Exception {
        service.updateSong(1, "Turtles", 200, 'B');
        Iterable<Song> songs = service.getSongs();
        long size = StreamSupport.stream(songs.spliterator(), false).count();
        assertEquals("still 3 songs after update", 3, size);

        Song updatedSong = StreamSupport.stream(songs.spliterator(), false)
                .filter(song -> song.getId() == 1).findFirst().orElseThrow(RuntimeException::new);
        assertEquals("new title", updatedSong.getDetails().getTitle(), "Turtles");
        assertEquals("new length", updatedSong.getDetails().getLength(), 200);
        assertEquals("new key", "B", updatedSong.getDetails().getKey().toString());
    }

    @Test
    public void getByExactName() throws Exception {
        long number = service.getByExactName("Stars");
        assertEquals("there is one song with title Johny", 1, number);
    }

    @Test
    public void getByNameLike() throws Exception {
        long number = service.getLikeName("r");
        assertEquals("there are tow matching names", 2, number);
    }
}
