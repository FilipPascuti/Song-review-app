package ro.ubb.songs.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "song")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Song implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Embedded
    private SongDetails details;

    private Integer artistId;

}
