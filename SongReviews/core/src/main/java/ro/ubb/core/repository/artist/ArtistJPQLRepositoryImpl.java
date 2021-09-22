package ro.ubb.core.repository.artist;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Artist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("ArtistJPQLRepositoryImpl")
public class ArtistJPQLRepositoryImpl implements ArtistCustomRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Artist> findByExactName(String name) {
        Query query = entityManager.createQuery(
                "select distinct a from Artist a where a.name = :name"
        );
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Artist> findByNameLike(String name) {
        Query query = entityManager.createQuery(
                "select distinct a from Artist a where a.name like :pattern"
        );
        query.setParameter("pattern", "%" + name + "%");
        return query.getResultList();
    }
}
