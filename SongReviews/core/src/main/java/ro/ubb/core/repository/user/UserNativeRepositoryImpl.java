package ro.ubb.core.repository.user;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("UserNativeRepositoryImpl")
public class UserNativeRepositoryImpl implements UserRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<User> findByExactName(String name) {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {u.*} from usertable u where u.name = :name")
                .addEntity("u", User.class)
                .setParameter("name", name)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<User> findByNameLike(String name) {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {u.*} from usertable u where u.name like :pattern")
                .addEntity("u", User.class)
                .setParameter("pattern", "%" + name + "%")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }
}
