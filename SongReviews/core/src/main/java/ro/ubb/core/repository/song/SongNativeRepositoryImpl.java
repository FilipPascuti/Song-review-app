package ro.ubb.core.repository.song;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.Song;
import ro.ubb.core.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("SongNativeRepositoryImpl")
public class SongNativeRepositoryImpl implements SongCustomRepository {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public Long countAllWithExactTitle(String title) {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {s.*} from song s where s.title = :title")
                .addEntity("s", Song.class)
                .setParameter("title", title)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (long) query.getResultList().size();
    }

    @Override
    @Transactional
    public Long countAllWithTitleLike(String title) {
        HibernateEntityManager hibernateEntityManager = entityManager.unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {s.*} from song s where s.title like :pattern")
                .addEntity("s", Song.class)
                .setParameter("pattern", "%" + title + "%")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (long) query.getResultList().size();
    }
}
