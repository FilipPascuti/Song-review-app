package ro.ubb.review.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.review.domain.Review;
import ro.ubb.review.domain.Song;
import ro.ubb.review.domain.User;
import ro.ubb.review.dto.ReviewDto;
import ro.ubb.review.exceptions.ElementNotFoundException;
import ro.ubb.review.repository.ReviewRepository;
import ro.ubb.review.validator.ReviewValidator;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReviewService {

    @Autowired
    ReviewRepository repository;

    @Autowired
    ReviewValidator validator;

    @Autowired
    RestTemplate restTemplate;

    public static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    /**
     * Creates, validates and adds a new review to the repository
     *
     * @param userID            - the ID of the user who posted the review
     * @param songID            - the ID of the song which is reviewed
     * @param starRating        - the rating in stars
     * @param reviewDescription - the content of the review
     *                          the same user of the same song
     */
    public void addReview(Integer userID, Integer songID, Integer starRating, String reviewDescription) {
        log.trace("Adding review with userID{}, songID{}, star rating{} and description{}", userID, songID, starRating, reviewDescription);

        String host = System.getenv("GATEWAY");

        try {
            User user =
                    restTemplate.getForObject("http://" + host + ":8080/api/users/" + userID,
                            User.class);
        } catch (RestClientException exception) {
            throw new ElementNotFoundException("user does not exist");
        }

        try {
            Song song =
                    restTemplate.getForObject("http://" + host + ":8080/api/songs/" + songID,
                            Song.class);
        } catch (RestClientException exception) {
            throw new ElementNotFoundException("song does not exist");
        }


        Review review = Review.builder()
                .userId(userID)
                .songId(songID)
                .stars(starRating)
                .reviewText(reviewDescription)
                .build();

        repository.save(review);

        log.trace("Added review");
    }

    /**
     * Modifies (and validates before saving) a review
     *
     * @param userID            - the ID of the user who posted the review
     * @param songID            - the ID of the song which is reviewed
     * @param starRating        - the rating in stars - must be between 1 and 5 to be validated
     * @param reviewDescription - the content of the review
     * @throws ElementNotFoundException if the user identified by userID
     *                                  has not reviewed the song identified the song songID
     */
    @Transactional
    public void updateReview(Integer userID, Integer songID, Integer starRating, String reviewDescription) {
        log.trace("Update review with userID{}, songID{}, star rating{} and description{}", userID, songID, starRating, reviewDescription);

        repository.findAll().stream()
                .filter(review1 -> review1.getUserId().equals(userID) && review1.getSongId().equals(songID))
                .findFirst()
                .ifPresentOrElse(
                        review -> {
                            review.setStars(starRating);
                            review.setReviewText(reviewDescription);
                        },
                        () -> {
                            throw new ElementNotFoundException("Review from user= " + userID + " to song" + songID + " does not exist");
                        }
                );

        log.trace("Updated review with userID{}, songID{}", userID, songID);
    }

    /**
     * Deletes a review identified by userID and songID
     *
     * @param userID - the ID of the user who posted the review
     * @param songID - the ID of the song which is reviewed
     * @throws ElementNotFoundException if there is no review identified by (userID, songID)
     */
    public void deleteReview(Integer userID, Integer songID) {
        log.trace("Removing review with id ({}, {})", userID, songID);

        var review = repository.findAll().stream()
                .filter(review1 -> review1.getUserId() == userID && review1.getSongId() == songID)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        repository.deleteById(review.getId());

        log.trace("Removed review with id ({}, {})", userID, songID);
    }

    /**
     * @return An iterable of all review entities
     */
    public List<ReviewDto> getAllReviews() {
        log.trace("getReviews --- method entered");

        String host = System.getenv("GATEWAY");

        var result = repository.findAll().stream()
                .map(review -> {

                    User user =
                            restTemplate.getForObject("http://" + host + ":8080/api/users/" + review.getUserId(),
                                    User.class);

                    Song song =
                            restTemplate.getForObject("http://" + host + ":8080/api/songs/" + review.getSongId(),
                                    Song.class);

                    return ReviewDto.builder()
                            .id(review.getId())
                            .user(user)
                            .song(song)
                            .stars(review.getStars())
                            .reviewText(review.getReviewText())
                            .build();
                })
                .collect(Collectors.toList());

        log.trace("got the reviews: {}", result);

        return result;
    }

    public ReviewDto getReview(int userId, int songId) {

        String host = System.getenv("GATEWAY");

        User user =
                restTemplate.getForObject("http://" + host + ":8080/api/users/" + userId,
                        User.class);

        Song song =
                restTemplate.getForObject("http://" + host + ":8080/api/songs/" + songId,
                        Song.class);

        var review = repository.findAll().stream()
                .filter(review1 -> review1.getUserId() == userId && review1.getSongId() == songId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return ReviewDto.builder()
                .id(review.getId())
                .user(user)
                .song(song)
                .stars(review.getStars())
                .reviewText(review.getReviewText())
                .build();
    }
}
