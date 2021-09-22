package ro.ubb.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
public class SongDto  extends BaseDto<Integer>{
    private SongDetailsDto details;
    private ArtistDto artist;
}
