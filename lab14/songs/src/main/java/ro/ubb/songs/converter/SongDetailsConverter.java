package ro.ubb.songs.converter;

import org.springframework.stereotype.Component;
import ro.ubb.songs.domain.SongDetails;
import ro.ubb.songs.dto.SongDetailsDto;

@Component
public class SongDetailsConverter extends BaseConverter<SongDetails, SongDetailsDto>{
    @Override
    public SongDetails convertDtoToModel(SongDetailsDto songDetailsDto) {
        return SongDetails.builder()
                .title(songDetailsDto.getTitle())
                .length(songDetailsDto.getLength())
                .key(songDetailsDto.getKey())
                .build();
    }

    @Override
    public SongDetailsDto convertModelToDto(SongDetails songDetails) {
        return SongDetailsDto.builder()
                .title(songDetails.getTitle())
                .length(songDetails.getLength())
                .key(songDetails.getKey())
                .build();
    }
}
