package ro.ubb.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
