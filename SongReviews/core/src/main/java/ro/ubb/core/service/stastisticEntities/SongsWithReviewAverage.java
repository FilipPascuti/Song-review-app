package ro.ubb.core.service.stastisticEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.ubb.core.domain.Song;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongsWithReviewAverage {
    private Song song;
    private Integer averageRating;
}
