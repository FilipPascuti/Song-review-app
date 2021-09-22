package ro.ubb.core.repository.song;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Song;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component("SongCriteriaRepositoryImpl")
public class SongCriteriaRepositoryImpl implements SongCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Long countAllWithExactTitle(String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Song> songRoot = criteriaQuery.from(Song.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.where(criteriaBuilder.equal(songRoot.get("details").get("title"), parameter));
        criteriaQuery.select(criteriaBuilder.count(songRoot));


        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, title);
        return query.getSingleResult();
    }

    @Override
    public Long countAllWithTitleLike(String title) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Song> songRoot = criteriaQuery.from(Song.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);

        criteriaQuery.select(criteriaBuilder.count(songRoot));
        criteriaQuery.where(criteriaBuilder.like(songRoot.get("details").get("title"), parameter));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, "%" + title + "%");
        return query.getSingleResult();
    }
}
