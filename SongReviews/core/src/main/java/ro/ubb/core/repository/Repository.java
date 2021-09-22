package ro.ubb.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.core.domain.BaseEntity;

import java.io.Serializable;

public interface Repository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
