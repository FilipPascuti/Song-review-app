package ro.ubb.core.repository.user;

import org.springframework.data.repository.query.Param;
import ro.ubb.core.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findByExactName(@Param("name") String name);

    List<User> findByNameLike(@Param("name") String name);

}
