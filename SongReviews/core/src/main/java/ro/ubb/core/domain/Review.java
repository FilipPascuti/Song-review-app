package ro.ubb.core.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "reviewId"))})
public class Review extends BaseEntity<Integer>{

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "songId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Song song;

    @Column(name = "stars")
    private int stars;

    @Column(name = "review_text")
    private String reviewText;
}
