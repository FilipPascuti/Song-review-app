package ro.ubb.core.repository.artist;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("ArtistCriteriaRepositoryImpl")
public class ArtistCriteriaRepositoryImpl implements ArtistCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Artist> findByExactName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = criteriaBuilder.createQuery(Artist.class);

        Root<Artist> artistRoot = criteriaQuery.from(Artist.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(artistRoot.get("name"), parameter));

        TypedQuery<Artist> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, name);
        return query.getResultList();
    }

    @Override
    public List<Artist> findByNameLike(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = criteriaBuilder.createQuery(Artist.class);

        Root<Artist> userRoot = criteriaQuery.from(Artist.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.like(userRoot.get("name"), parameter));

        TypedQuery<Artist> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, "%" + name + "%");
        return query.getResultList();
    }
}
