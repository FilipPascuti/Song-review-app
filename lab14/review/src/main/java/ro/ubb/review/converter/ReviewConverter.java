package ro.ubb.review.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.review.domain.Review;
import ro.ubb.review.domain.Song;
import ro.ubb.review.dto.ReviewDto;

@Component
public class ReviewConverter extends BaseConverter<Review, ReviewDto>{

    @Override
    public Review convertDtoToModel(ReviewDto reviewDto) {
        Review review = Review.builder()
                .id(reviewDto.getId())
                .userId(reviewDto.getUser().getId())
                .songId(reviewDto.getSong().getId())
                .stars(reviewDto.getStars())
                .reviewText(reviewDto.getReviewText())
                .build();
        return review;
    }

    @Override
    public ReviewDto convertModelToDto(Review review) {
        return null;
    }
}
