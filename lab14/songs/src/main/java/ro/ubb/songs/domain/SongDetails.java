package ro.ubb.songs.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SongDetails implements Serializable {
    @Column(name = "title")
    private String title;

    @Column(name = "length")
    private int length;

    @Column(name = "key")
    private Character key;
}
