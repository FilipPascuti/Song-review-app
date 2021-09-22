package ro.ubb.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.core.domain.Artist;
import ro.ubb.core.domain.Song;
import ro.ubb.web.dto.SongDto;

@Component
public class SongConverter extends BaseConverter<Song, SongDto>{

    @Autowired
    SongDetailsConverter detailsConverter;

    @Autowired
    ArtistConverter artistConverter;

    @Override
    public Song convertDtoToModel(SongDto songDto) {

        Artist artist = artistConverter.convertDtoToModel(songDto.getArtist());

        Song song = Song.builder()
                .details(detailsConverter.convertDtoToModel(songDto.getDetails()))
                .artist(artist)
                .build();
        song.setId(songDto.getId());
        return song;
    }

    @Override
    public SongDto convertModelToDto(Song song) {
        SongDto dto = SongDto.builder()
                .details(detailsConverter.convertModelToDto(song.getDetails()))
                .artist(artistConverter.convertModelToDto(song.getArtist()))
                .build();
        dto.setId(song.getId());
        return dto;
    }
}
