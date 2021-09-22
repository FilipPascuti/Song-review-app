package ro.ubb.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Review;
import ro.ubb.core.domain.Song;
import ro.ubb.core.domain.User;
import ro.ubb.web.dto.ReviewDto;


@Component
public class ReviewConverter extends BaseConverter<Review, ReviewDto>{

    @Autowired
    UserConverter userConverter;

    @Autowired
    SongConverter songConverter;

    @Override
    public Review convertDtoToModel(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        User user = userConverter.convertDtoToModel(reviewDto.getUser());
        Song song = songConverter.convertDtoToModel(reviewDto.getSong());
        review.setUser(user);
        review.setSong(song);
        review.setStars(reviewDto.getStars());
        review.setReviewText(reviewDto.getReviewText());
        return review;
    }

    @Override
    public ReviewDto convertModelToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUser(userConverter.convertModelToDto(review.getUser()));
        dto.setSong(songConverter.convertModelToDto(review.getSong()));
        dto.setStars(review.getStars());
        dto.setReviewText(review.getReviewText());
        return dto;
    }
}
