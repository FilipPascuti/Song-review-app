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
import ro.ubb.core.domain.User;
import ro.ubb.core.repository.user.UserRepository;

import static org.junit.Assert.assertEquals;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-users.xml")
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void findAll() throws Exception {
        List<User> users = repository.findAll();
        assertEquals("there should be 3 users", 3, users.size());
    }

    @Test
    public void add() throws Exception {
        User user = User.builder()
                .name("Marian")
                .build();
        user.setId(-1);
        repository.save(user);
        List<User> users = repository.findAll();
        assertEquals("there should be 4 users instead of 3", 4, users.size());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        User user = repository.findById(1).orElseThrow(RuntimeException::new);
        user.setName("Dorinel");
        List<User> users = repository.findAll();
        assertEquals("name updated corectly", "Dorinel", users.get(0).getName());
    }

    @Test
    public void delete() throws Exception {
        repository.deleteById(2);
        List<User> users= repository.findAll();
        assertEquals("the size show now be 2", 2, users.size());
    }
}
