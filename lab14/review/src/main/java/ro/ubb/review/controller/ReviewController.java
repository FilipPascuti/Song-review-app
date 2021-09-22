package ro.ubb.review.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.review.converter.ReviewConverter;
import ro.ubb.review.domain.Review;
import ro.ubb.review.dto.ReviewDto;
import ro.ubb.review.exceptions.AlreadyExistingElementException;
import ro.ubb.review.exceptions.ElementNotFoundException;
import ro.ubb.review.service.ReviewService;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService service;

    @Autowired
    ReviewConverter converter;

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @RequestMapping(value = "reviews", method = RequestMethod.GET)
    public List<ReviewDto> getReviews(){
        log.trace("fetching reviews");
        return service.getAllReviews();
    }

    @RequestMapping(value = "reviews/{userId}/{songId}", method = RequestMethod.GET)
    public ReviewDto getReview(@PathVariable int userId, @PathVariable int songId){
        log.trace("fetching song");
        return service.getReview(userId, songId);
    }

    @RequestMapping(value = "reviews/add", method = RequestMethod.POST)
    public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto){
        log.trace("adding a review");
        Review review = converter.convertDtoToModel(reviewDto);
        try{
            service.addReview(review.getUserId(), review.getSongId(), review.getStars(), review.getReviewText());
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
            service.updateReview(review.getUserId(), review.getSongId(), review.getStars(), review.getReviewText());
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

}
