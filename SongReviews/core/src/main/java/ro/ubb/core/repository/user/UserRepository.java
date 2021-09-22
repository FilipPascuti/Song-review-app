package ro.ubb.core.repository.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.core.domain.User;
import ro.ubb.core.repository.Repository;

import java.util.List;
import java.util.Optional;

//@Component("UserJPQLRepository")
//@Component("UserCriteriaRepository")
@Component("UserNativeRepository")
public interface UserRepository extends Repository<User, Integer>, UserRepositoryCustom {

    @Query("select distinct user from User user")
    @EntityGraph(value = "userWithRentalsAndSongs", type = EntityGraph.EntityGraphType.LOAD)
    List<User> findAllUsersWithRentals();

    @Query("select distinct user from User user where user.id=:userId")
    @EntityGraph(value = "userWithRentalsAndSongs", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByIdWithReviews(@Param("userId") Integer userId);
}
