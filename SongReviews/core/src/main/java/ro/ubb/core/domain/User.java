package ro.ubb.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "userTable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder

@NamedEntityGraphs({
        @NamedEntityGraph(name = "userWithReviews",
                attributeNodes = @NamedAttributeNode(value = "reviews")),
        @NamedEntityGraph(name = "userWithRentalsAndSongs",
                attributeNodes = @NamedAttributeNode(value = "reviews",
                        subgraph = "reviewWithSong"
                ),
                subgraphs = @NamedSubgraph(name = "reviewWithSong",
                        attributeNodes = @NamedAttributeNode(value = "song")
                )
        )
})

@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "userId"))})
public class User extends BaseEntity<Integer>{

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Review> reviews;
}
