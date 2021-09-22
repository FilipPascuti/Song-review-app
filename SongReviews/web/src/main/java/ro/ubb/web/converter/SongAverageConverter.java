package ro.ubb.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.core.service.stastisticEntities.SongsWithReviewAverage;
import ro.ubb.web.dto.SongAverageDto;

@Component
public class SongAverageConverter  extends BaseConverter<SongsWithReviewAverage, SongAverageDto>{

    @Autowired
    SongConverter converter;


    @Override
    public SongsWithReviewAverage convertDtoToModel(SongAverageDto songAverageDto) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public SongAverageDto convertModelToDto(SongsWithReviewAverage songsWithReviewAverage) {
        return SongAverageDto.builder()
                .song(converter.convertModelToDto(songsWithReviewAverage.getSong()))
                .average(songsWithReviewAverage.getAverageRating())
                .build();
    }
}
