package ro.ubb.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder

@NamedEntityGraphs({
        @NamedEntityGraph(name = "artistWithSongs",
                attributeNodes = @NamedAttributeNode(value = "songs")),
})

@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = "artistId"))})
public class Artist extends BaseEntity<Integer> {

    private String name;

    private String description;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Song> songs;

}
