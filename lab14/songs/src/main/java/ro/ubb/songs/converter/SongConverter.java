package ro.ubb.songs.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.songs.domain.Artist;
import ro.ubb.songs.domain.Song;
import ro.ubb.songs.dto.SongDto;

@Component
public class SongConverter extends BaseConverter<Song, SongDto> {

    @Autowired
    SongDetailsConverter detailsConverter;

    @Override
    public Song convertDtoToModel(SongDto songDto) {

        Song song = Song.builder()
                .details(songDto.getDetails())
                .artistId(songDto.getArtist().getId())
                .build();
        song.setId(songDto.getId());
        return song;
    }

    @Override
    public SongDto convertModelToDto(Song song) {
//        SongDto dto = SongDto.builder()
//                .details(detailsConverter.convertModelToDto(song.getDetails()))
//                .artist(artistConverter.convertModelToDto(song.getArtist()))
//                .build();
//        dto.setId(song.getId());
//        return dto;
        return null;
    }
}
