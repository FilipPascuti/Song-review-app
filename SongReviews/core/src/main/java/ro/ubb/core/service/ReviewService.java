package ro.ubb.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.domain.*;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.repository.song.SongRepository;
import ro.ubb.core.repository.user.UserRepository;
import ro.ubb.core.service.stastisticEntities.ActiveUser;
import ro.ubb.core.service.stastisticEntities.SongsWithReviewAverage;
import ro.ubb.core.validation.ReviewValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReviewService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    ReviewValidator validator;

    public static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    /**
     * This function checks whether an userId exists in the user repository
     * @param userId: the ID of the user we will do the checking
     * @throws ElementNotFoundException if the userId does not exist in the user repository
     */
    private void checkUserId(Integer userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new ElementNotFoundException("User with ID " + userId + " not found"));
    }

    /**
     * This function checks whether an songId exists in the song repository
     * @param songId: the ID of the song we will do the checking
     * @throws ElementNotFoundException if the songId does not exist in the song repository
     */
    private void checkSongId(Integer songId){
        songRepository.findById(songId)
                .orElseThrow(() -> new ElementNotFoundException("Song with ID " + songId + " not found"));
    }

    /**
     * Creates, validates and adds a new review to the repository
     * @param userID - the ID of the user who posted the review
     * @param songID - the ID of the song which is reviewed
     * @param starRating - the rating in stars
     * @param reviewDescription - the content of the review
     * @throws AlreadyExistingElementException if we already have a review posted by
     * the same user of the same song
     */
    @Transactional
    public void addReview(Integer userID, Integer songID, Integer starRating, String reviewDescription){
        log.trace("Adding review with userID{}, songID{}, star rating{} and description{}", userID, songID, starRating, reviewDescription);
        checkUserId(userID);
        checkSongId(songID);
        User user = userRepository.findByIdWithReviews(userID).get();
        Song song = songRepository.findByIdWithReviews(songID).get();

        userRepository.findAllUsersWithRentals().stream()
                .map(userr -> userr.getReviews())
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(review -> review.getSong().getId().equals(songID) && review.getUser().getId().equals(userID))
                .findFirst()
                .ifPresent(
                        (val) -> {throw new ElementNotFoundException("Review from user= " + userID + " to song" + songID + " already exist");}
                );


        Review review = Review.builder()
                .user(user)
                .song(song)
                .stars(starRating)
                .reviewText(reviewDescription)
                .build();
        user.getReviews().add(review);

        log.trace("Added review");
    }

    /**
     * Modifies (and validates before saving) a review
     * @param userID - the ID of the user who posted the review
     * @param songID - the ID of the song which is reviewed
     * @param starRating - the rating in stars - must be between 1 and 5 to be validated
     * @param reviewDescription - the content of the review
     * @throws ElementNotFoundException if the user identified by userID
     * has not reviewed the song identified the song songID
     */
    @Transactional
    public void updateReview(Integer userID, Integer songID, Integer starRating, String reviewDescription){
        log.trace("Update review with userID{}, songID{}, star rating{} and description{}", userID, songID, starRating, reviewDescription);

        userRepository.findAllUsersWithRentals().stream()
                .map(user -> user.getReviews())
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(review -> review.getSong().getId().equals(songID) && review.getUser().getId().equals(userID))
                .findFirst()
                .ifPresentOrElse(
                        review -> {
                            review.setStars(starRating);
                            review.setReviewText(reviewDescription);
                        },
                        () -> {throw new ElementNotFoundException("Review from user= " + userID + " to song" + songID + " does not exist");}
                );


        log.trace("Updated review with userID{}, songID{}", userID, songID);
    }

    /**
     * Deletes a review identified by userID and songID
     * @param userID - the ID of the user who posted the review
     * @param songID - the ID of the song which is reviewed
     * @throws ElementNotFoundException if there is no review identified by (userID, songID)
     */
    @Transactional
    public void deleteReview(Integer userID, Integer songID){
        log.trace("Removing review with id ({}, {})", userID, songID);

        Optional<Review> optionalReview = userRepository.findAllUsersWithRentals().stream()
                .map(user -> user.getReviews())
                .flatMap(reviews -> reviews.stream())
                .filter(review -> review.getSong().getId().equals(songID) && review.getUser().getId().equals(userID))
                .findFirst();
        log.trace("review: {}", optionalReview.get());
        optionalReview.ifPresent(
                review -> {
                    Optional<User> user = userRepository.findByIdWithReviews(review.getUser().getId());
                    log.trace("user: {}", user.get());
                    user.ifPresent(u -> u.getReviews().remove(review));
                });

        log.trace("Removed review with id ({}, {})", userID, songID);
    }

    /**
     * @return An iterable of all review entities
     */
    public Iterable<Review> getAllReviews(){
        log.trace("getReviews --- method entered");

        var result = songRepository.findAllSongsWithRentals().stream()
                .map(song -> song.getReviews())
                .reduce(new ArrayList<>(), (a,b) -> {
                    a.addAll(b);
                    return a;
                });

        log.trace("got the reviews: {}", result);

        return result;
    }

    public Review getReview(int userId, int songId) {
        return songRepository.findAllSongsWithRentals().stream()
                .map(song -> song.getReviews())
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(review -> review.getSong().getId().equals(songId) && review.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(
                        () -> {throw new ElementNotFoundException("Review from user= " + userId + " to song" + songId + " does not exist");}
                );
    }

    public List<SongsWithReviewAverage> getSongAverages() {
        return this.songRepository.findAllSongsWithRentals().stream()
                .map(song -> {
                    if(song.getReviews().size() > 0) {
                        Integer sum = song.getReviews()
                                .stream()
                                .map(review -> review.getStars())
                                .reduce(0, Integer::sum);
                        var average = sum / song.getReviews().size();
                        return new SongsWithReviewAverage(song, average);
                    }
                    return new SongsWithReviewAverage(song, 0);
                }).collect(Collectors.toList());
    }

    public List<ActiveUser> getUsersActivity() {
        return this.userRepository.findAllUsersWithRentals().stream()
                .map(user -> {
                    return ActiveUser.builder()
                            .user(user)
                            .activity(user.getReviews().size())
                            .build();
                }).collect(Collectors.toList());
    }

}
