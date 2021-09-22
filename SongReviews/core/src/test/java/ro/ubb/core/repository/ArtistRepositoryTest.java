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
import ro.ubb.core.domain.User;
import ro.ubb.core.repository.artist.ArtistRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-artists.xml")
public class ArtistRepositoryTest {

    @Autowired
    ArtistRepository repository;

    @Test
    public void findAll() throws Exception {
        List<Artist> artists = repository.findAll();
        assertEquals("there should be 3 users", 3, artists.size());
    }

    @Test
    public void add() throws Exception {
        Artist artist = Artist.builder()
                .name("Todor")
                .description("trapper")
                .build();
        artist.setId(-1);
        repository.save(artist);
        List<Artist> artists = repository.findAll();
        assertEquals("there should be 4 users instead of 3", 4, artists.size());
    }
    @Test
    @Transactional
    public void update() throws Exception {
        Artist artist = repository.findById(1).orElseThrow(RuntimeException::new);
        artist.setName("Dorinel");
        List<Artist> artists = repository.findAll();
        assertEquals("name updated corectly", "Dorinel", artists.get(0).getName());
    }

    @Test
    public void delete() throws Exception {
        repository.deleteById(2);
        List<Artist> artists= repository.findAll();
        assertEquals("the size show now be 2", 2, artists.size());
    }
}
