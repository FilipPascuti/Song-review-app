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
import ro.ubb.core.domain.User;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-artists.xml")
public class ArtistServiceTest {
    @Autowired
    ArtistService service;

    @Test
    public void getArtists() throws Exception {
        Iterable<Artist> artists = service.getArtists();
        long size = StreamSupport.stream(artists.spliterator(), false).count();
        assertEquals("there should be 3 artists", 3, size);
    }

    @Test
    public void addArtist() throws Exception {
        service.addArtist("Marius", "trapper");
        Iterable<Artist> artists = service.getArtists();
        long size = StreamSupport.stream(artists.spliterator(), false).count();

        assertEquals("added new artist, 4 instead of 3 artists", 4, size);
    }

    @Test
    public void deleteArtist() throws Exception {
        service.deleteArtist(1);
        Iterable<Artist> artists = service.getArtists();
        long size = StreamSupport.stream(artists.spliterator(), false).count();

        assertEquals("deleted a artist", 2, size);
    }

    @Test
    public void updateArtist() throws Exception {
        service.updateArtist(1, "Dorinel", "rocker");
        Iterable<Artist> users = service.getArtists();
        long size = StreamSupport.stream(users.spliterator(), false).count();
        assertEquals("still 3 artists after update", 3, size);

        Artist updatedArtist = StreamSupport.stream(users.spliterator(), false)
                .filter(artist -> artist.getId() == 1).findFirst().orElseThrow(RuntimeException::new);
        assertEquals("new name", updatedArtist.getName(), "Dorinel");
        assertEquals("new description", updatedArtist.getDescription(), "rocker");
    }

    @Test
    public void getByExactName() throws Exception {
        List<Artist> artists = service.getByExactName("Johny");
        assertEquals("there is one artist with name Johny", 1, artists.size());
    }

    @Test
    public void getByNameLike() throws Exception {
        List<Artist> artists = service.getLikeName("r");
        assertEquals("there are tow matching names", 2, artists.size());
    }
}
