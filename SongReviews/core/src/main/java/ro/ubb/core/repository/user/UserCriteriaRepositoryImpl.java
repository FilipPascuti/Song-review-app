package ro.ubb.core.repository.user;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("UserCriteriaRepositoryImpl")
public class UserCriteriaRepositoryImpl implements UserRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findByExactName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("name"), parameter));

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, name);
        return query.getResultList();
    }

    @Override
    public List<User> findByNameLike(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.like(userRoot.get("name"), parameter));

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, "%" + name + "%");
        return query.getResultList();
    }
}
