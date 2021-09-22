package ro.ubb.review.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@ToString()
@Builder
public class Review implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "songId")
    private Integer songId;

    @Column(name = "stars")
    private int stars;

    @Column(name = "review_text")
    private String reviewText;

}
