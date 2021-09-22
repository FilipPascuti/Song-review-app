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
import ro.ubb.core.domain.User;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-users.xml")
public class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    public void getUsers() throws Exception {
        Iterable<User> users = service.getUsers();
        long size = StreamSupport.stream(users.spliterator(), false).count();
        assertEquals("there should be 3 users", 3, size);
    }

    @Test
    public void addUser() throws Exception {
        service.addUser("Marius");
        Iterable<User> users = service.getUsers();
        long size = StreamSupport.stream(users.spliterator(), false).count();

        assertEquals("added new user, 4 instead of 3 users", 4, size);
    }

    @Test
    public void deleteUser() throws Exception {
        service.deleteUser(1);
        Iterable<User> users = service.getUsers();
        long size = StreamSupport.stream(users.spliterator(), false).count();

        assertEquals("deleted a user", 2, size);
    }

    @Test
    public void updateUser() throws Exception {
        service.updateUser(1, "Dorinel");
        Iterable<User> users = service.getUsers();
        long size = StreamSupport.stream(users.spliterator(), false).count();
        assertEquals("still 3 users after update", 3, size);

        User updatedUser = StreamSupport.stream(users.spliterator(), false)
                .filter(user -> user.getId() == 1).findFirst().orElseThrow(RuntimeException::new);
        assertEquals("new name", updatedUser.getName(), "Dorinel");
    }

    @Test
    public void getByExactName() throws Exception {
        List<User> users = service.getByExactName("Dorel");
        assertEquals("there is one user with name Dorel", 1, users.size());
    }

    @Test
    public void getByNameLike() throws Exception {
        List<User> users = service.getLikeName("rel");
        assertEquals("there are tow matching names", 2, users.size());
    }
}
