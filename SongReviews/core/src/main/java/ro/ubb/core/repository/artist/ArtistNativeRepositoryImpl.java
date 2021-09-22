package ro.ubb.core.repository.artist;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.Artist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("ArtistNativeRepositoryImpl")
public class ArtistNativeRepositoryImpl implements ArtistCustomRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Artist> findByExactName(String name) {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {a.*} from artist a where a.name = :name")
                .addEntity("a", Artist.class)
                .setParameter("name", name)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Artist> findByNameLike(String name) {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {a.*} from artist a where a.name like :pattern")
                .addEntity("a", Artist.class)
                .setParameter("pattern", "%" + name + "%")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }
}
