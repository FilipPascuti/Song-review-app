package ro.ubb.core.repository.user;


import org.springframework.stereotype.Component;
import ro.ubb.core.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("UserJPQLRepositoryImpl")
public class UserJPQLRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findByExactName(String name) {
        Query query = entityManager.createQuery(
                "select distinct u from User u where u.name = :name"
        );
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<User> findByNameLike(String name) {
        Query query = entityManager.createQuery(
                "select distinct u from User u where u.name like :pattern"
        );
        query.setParameter("pattern", "%" + name + "%");
        return query.getResultList();
    }
}
