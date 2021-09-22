package ro.ubb.core.validation;

import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Review;
import ro.ubb.core.exceptions.ValidatorException;

import java.util.Optional;

@Component
public class ReviewValidator implements Validator<Review> {

    /**
     * @param entity a review object that has to be validated
     * In order to be valid, a review has to contain the id of a valid song and user
     *            and have a rating between 1 and 5 stars
     * The review text can be empty, but it must not exceed 500 characters
     * @throws ValidatorException
     */
    @Override
    public void validate(Review entity) throws ValidatorException {
        Optional.of(entity).filter(review -> review.getUser().getId() >= 0)
                .orElseThrow(() -> new ValidatorException("Invalid user ID"));
        Optional.of(entity).filter(review -> review.getSong().getId() >= 0)
                .orElseThrow(() -> new ValidatorException("Invalid song ID"));
        Optional.of(entity).filter(review -> review.getStars() >= 1 && review.getStars() <= 5)
                .orElseThrow(() -> new ValidatorException("Rating should be between 1 and 5"));
        Optional.of(entity).filter(review -> review.getReviewText().length() <= 500)
                .orElseThrow(() -> new ValidatorException("Review text cannot exceed 500 characters"));
    }
}
