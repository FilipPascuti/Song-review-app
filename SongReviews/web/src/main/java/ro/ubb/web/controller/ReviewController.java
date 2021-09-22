package ro.ubb.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.domain.Review;
import ro.ubb.core.domain.Song;
import ro.ubb.core.exceptions.AlreadyExistingElementException;
import ro.ubb.core.exceptions.ElementNotFoundException;
import ro.ubb.core.service.ReviewService;
import ro.ubb.core.service.stastisticEntities.SongsWithReviewAverage;
import ro.ubb.web.converter.ActiveUserConverter;
import ro.ubb.web.converter.ReviewConverter;
import ro.ubb.web.converter.SongAverageConverter;
import ro.ubb.web.converter.SongConverter;
import ro.ubb.web.dto.ActiveUserDto;
import ro.ubb.web.dto.ReviewDto;
import ro.ubb.web.dto.SongAverageDto;
import ro.ubb.web.dto.SongDto;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService service;

    @Autowired
    ReviewConverter converter;

    @Autowired
    SongAverageConverter songAverageConverter;

    @Autowired
    ActiveUserConverter activeUserConverter;

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @RequestMapping(value = "reviews", method = RequestMethod.GET)
    public List<ReviewDto> getReviews(){
        log.trace("fetching reviews");
        return converter.convertModelsToDtos(service.getAllReviews());
    }

    @RequestMapping(value = "reviews/{userId}/{songId}", method = RequestMethod.GET)
    public ReviewDto getReview(@PathVariable int userId, @PathVariable int songId){
        log.trace("fetching song");
        return converter.convertModelToDto(service.getReview(userId, songId));
    }

    @RequestMapping(value = "reviews/add", method = RequestMethod.POST)
    public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto){
        log.trace("adding a review");
        Review review = converter.convertDtoToModel(reviewDto);
        try{
            service.addReview(review.getUser().getId(), review.getSong().getId(), review.getStars(), review.getReviewText());
        } catch (AlreadyExistingElementException e){
            log.trace("review not added");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("review added");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "reviews/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateReview(@RequestBody ReviewDto reviewDto){
        log.trace("updating a review");
        Review review = converter.convertDtoToModel(reviewDto);
        try{
            service.updateReview(review.getUser().getId(), review.getSong().getId(), review.getStars(), review.getReviewText());
        } catch (ElementNotFoundException e){
            log.trace("review not updated");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("review updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "reviews/delete/{userId}/{songId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReview(@PathVariable int userId, @PathVariable int songId){
        log.trace("deleting a review");
        try{
            service.deleteReview(userId, songId);
        } catch (ElementNotFoundException e){
            log.trace("review not deleted");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("review deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "reviews/song-averages", method = RequestMethod.GET)
    public List<SongAverageDto> getAverages(){
        return songAverageConverter.convertModelsToDtos(this.service.getSongAverages());
    }

    @RequestMapping(value = "reviews/active-users", method = RequestMethod.GET)
    public List<ActiveUserDto> getActiveUsers(){
        return activeUserConverter.convertModelsToDtos(this.service.getUsersActivity());
    }
}
