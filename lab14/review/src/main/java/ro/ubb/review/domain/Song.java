package ro.ubb.review.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Song implements Serializable {

    private Integer id;

    @Embedded
    private SongDetails details;

    private Artist artist;

}
