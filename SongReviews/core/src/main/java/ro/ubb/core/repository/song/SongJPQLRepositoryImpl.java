package ro.ubb.core.repository.song;


import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Song;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("SongJPQLRepositoryImpl")
public class SongJPQLRepositoryImpl implements SongCustomRepository{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Long countAllWithExactTitle(String title) {
        Query query = entityManager.createQuery(
                "select count(s.details.title) from Song s where s.details.title = :title"
        );
        query.setParameter("title", title);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long countAllWithTitleLike(String title) {
        Query query = entityManager.createQuery(
                "select count(s.details.title) from Song s where s.details.title like :pattern"
        );
        query.setParameter("pattern", "%" + title + "%");
        return (Long) query.getSingleResult();
    }
}
