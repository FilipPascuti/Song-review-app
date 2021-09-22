package ro.ubb.review.dto;

import lombok.*;
import ro.ubb.review.domain.Song;
import ro.ubb.review.domain.User;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ReviewDto implements Serializable {
    private Integer id;
    private User user;
    private Song song;
    private int stars;
    private String reviewText;
}
