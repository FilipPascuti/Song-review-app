package ro.ubb.core.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "song")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder

@NamedEntityGraphs({
        @NamedEntityGraph(name = "songWithReviews",
                attributeNodes = @NamedAttributeNode(value = "reviews")),
        @NamedEntityGraph(name = "songWithRentalsAndUsers",
                attributeNodes = {
                        @NamedAttributeNode(value = "artist"),
                        @NamedAttributeNode(value = "reviews", subgraph = "reviewWithUser")
                },
                subgraphs = @NamedSubgraph(name = "reviewWithUser",
                        attributeNodes = @NamedAttributeNode(value = "user")
                )
        )
})

@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "songId"))})
public class Song extends BaseEntity<Integer>{

    @Embedded
    private SongDetails details;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "artistId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Artist artist;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Review> reviews;
}
