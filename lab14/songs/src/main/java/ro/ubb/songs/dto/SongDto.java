package ro.ubb.songs.dto;


import lombok.*;
import ro.ubb.songs.domain.Artist;
import ro.ubb.songs.domain.SongDetails;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class SongDto implements Serializable {
    private Integer id;
    private SongDetails details;
    private Artist artist;
}
